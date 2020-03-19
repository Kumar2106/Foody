package com.example.foody.adapter

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.foody.R
import com.example.foody.favourite_database.favourite_database
import com.example.foody.favourite_database.favourite_entity
import com.example.foody.model.restaurants
import com.squareup.picasso.Picasso

class favourite_adapter(val context: Context, var favourite_list: List<favourite_entity>) :
    RecyclerView.Adapter<favourite_adapter.favorite_adapter_view>() {


    class favorite_adapter_view(val view: View) : RecyclerView.ViewHolder(view) {
        val resturantimage: ImageView = view.findViewById(R.id.resturanatimage)
        val resturantname: TextView = view.findViewById(R.id.restuarntname)
        val resturant_price: TextView = view.findViewById(R.id.resturantsprice)
        val restaurants_rating: TextView = view.findViewById(R.id.restaurantrating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): favorite_adapter_view {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.restaurants_detail, parent, false)
        return favorite_adapter_view(view)
    }

    override fun getItemCount(): Int {
        return favourite_list.size
    }

    override fun onBindViewHolder(holder: favorite_adapter_view, position: Int) {
        val favourite_list = favourite_list[position]
        Log.i("rename",favourite_list.restaurants_name)
        holder.resturantname.text =  favourite_list.restaurants_name
        holder.restaurants_rating.text = favourite_list.restaurants_rating
        holder.resturant_price.text = favourite_list.restuarants_Cost
        Picasso.get().load(favourite_list.image).error(R.drawable.image_default).into(holder.resturantimage)
        holder.restaurants_rating.setOnClickListener(View.OnClickListener {
            val databse_access = favourite_db(context,favourite_list).execute()
            if (databse_access.get()){
                Toast.makeText(context,"Entity was removed from the favourite database",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(context,"Entity was not removed from the favourite database",Toast.LENGTH_LONG).show()
            }
        })

    }
    class favourite_db(val context: Context,val favouriteEntity: favourite_entity):AsyncTask<Void,Void,Boolean>(){
        override fun doInBackground(vararg params: Void?): Boolean {
            val database = Room.databaseBuilder(context,favourite_database::class.java,"favouite_db").build()
            database.favourite_Dao().delet(favouriteEntity)
            database.close()
            return true
        }

    }

}