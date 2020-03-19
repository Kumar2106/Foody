package com.example.foody.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.foody.R
import com.example.foody.activity.resdetails
import com.example.foody.favourite_database.favourite_database
import com.example.foody.favourite_database.favourite_entity
import com.example.foody.model.restaurants
import com.squareup.picasso.Picasso

class homeadapter(val context: Context, val reslits: ArrayList<restaurants>) :
    RecyclerView.Adapter<homeadapter.homeadapterviewholder>() {
    class homeadapterviewholder(view: View) : RecyclerView.ViewHolder(view) {
        //        val menuno:TextView = view.findViewById(R.id.menuno)
        val restaurantsname: TextView = view.findViewById(R.id.restuarntname)
        val restaurantsimage: ImageView = view.findViewById(R.id.resturanatimage)
        val resturantsprice: TextView = view.findViewById(R.id.resturantsprice)
        val restaurantsrating: TextView = view.findViewById(R.id.restaurantrating)
        val restaurantscontent: LinearLayout = view.findViewById(R.id.restuarnatcontains)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): homeadapterviewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.restaurants_detail, parent, false)
        return homeadapterviewholder(view)
    }

    override fun getItemCount(): Int {
        return reslits.size
    }

    override fun onBindViewHolder(holder: homeadapterviewholder, position: Int) {
        val resturant = reslits[position]
        var resid = resturant.resid
        holder.restaurantsname.text = resturant.resname
        holder.restaurantsrating.text = resturant.resrating
        holder.resturantsprice.text = "Rs.${resturant.rescost}/person"
        holder.restaurantscontent.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, resdetails::class.java)
            intent.putExtra("resid", resid)
            intent.putExtra("resname", resturant.resname)
            context.startActivity(intent)
        })
        Picasso.get().load(resturant.resimage).error(R.drawable.image_default)
            .into(holder.restaurantsimage)
        holder.restaurantsrating.setOnClickListener(View.OnClickListener {
            val favourite_restaurants = favourite_entity(
                resturant.resid,
                resturant.resimage,
                resturant.resname,
                resturant.rescost,
                resturant.resrating
            )
            if (!favorite_db_access(context, 1, favourite_restaurants).execute().get()) {
                val favorite_access =
                    favorite_db_access(context, 2, favourite_restaurants).execute()
                if (favorite_access.get()) {
                    holder.restaurantsrating.setBackgroundColor(Color.parseColor("#eb3434"))
                    Toast.makeText(context, "Restaurant was added successfully", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        context,
                        "Restaurant was not added successfully",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            else{
                val favorite_access = favorite_db_access(context,3,favourite_restaurants).execute()
                if (favorite_access.get()){
                    holder.restaurantsrating.setBackgroundColor(Color.parseColor("#A4B0BD"))
                    Toast.makeText(context,"Restaurant was removed from the database",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(context,"Restaurants was not removed from the database",Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    class favorite_db_access(
        val context: Context,
        val mode: Int,
        val favourite_res: favourite_entity
    ) : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            val db =
                Room.databaseBuilder(context, favourite_database::class.java, "favouite_db").build()
            when (mode) {
                1 -> {
                   val favorit=  db.favourite_Dao().favourit_by_id(favourite_res.resid.toString())
                    db.close()
                    return favorit != null
                }
                2 -> {
                    db.favourite_Dao().insert(favourite_res)
                    db.close()
                    return true
                }
                3 -> {
                    db.favourite_Dao().delet(favourite_res)
                    db.close()
                    return true
                }
            }
            return false
        }

    }

}