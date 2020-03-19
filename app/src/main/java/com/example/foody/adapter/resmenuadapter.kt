package com.example.foody.adapter

import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import cart_database.database
import cart_database.menuitem
import com.example.foody.R
import com.example.foody.model.menu_item

class resmenuadapter(val context: Context, val menulist: ArrayList<menu_item>) :
    RecyclerView.Adapter<resmenuadapter.menuadapter>() {
    var buttonclicke: Boolean? = false
    lateinit var view1 :View

    class menuadapter(val view: View) : RecyclerView.ViewHolder(view) {
        val menuname: TextView = view.findViewById(R.id.menuitemname)
        val menuno: TextView = view.findViewById(R.id.menuno)
        val menuitemcost: TextView = view.findViewById(R.id.menuitemcost)
        val btnaddtocart: Button = view.findViewById(R.id.btnaddtocart)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): menuadapter {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_list, parent, false)
        view1 = LayoutInflater.from(parent.context).inflate(R.layout.activity_resdetails,parent,false)
        val button:Button = view1.findViewById(R.id.btncart)
        button.text = "hi"
        return menuadapter(view)
    }

    override fun getItemCount(): Int {
        return menulist.size

    }

    override fun onBindViewHolder(holder: menuadapter, position: Int) {
        var menu = menulist[position]
        holder.menuname.text = menu.name
        holder.menuitemcost.append(menu.cost)
        var menuposition = position + 1
        holder.menuno.text = menuposition.toString()
        holder.btnaddtocart.setOnClickListener(View.OnClickListener {
            val local_menu_item = menuitem(menu.menuid, menu.name, menu.cost, menu.resid)
            if (!task(context, local_menu_item, 1).execute().get()) {
                val adding = task(context, local_menu_item, 2).execute()
                if (adding.get()) {
                    holder.btnaddtocart.text = "REMOVE"
                    holder.btnaddtocart.setBackgroundColor(Color.parseColor("#f1c40f"))
                    val button:Button = view1.findViewById(R.id.btncart)
                    button.visibility = View.GONE
                    button.text = "hi"

                } else {
                    Toast.makeText(
                        context,
                        "There was some error while adding it to the database",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                val removing = task(context, local_menu_item, 3).execute()
                if (removing.get()) {
                    holder.btnaddtocart.text = "ADD"
                    holder.btnaddtocart.setBackgroundColor(Color.parseColor("#eb3434"))
                } else {
                    Toast.makeText(
                        context,
                        "There were some problem in removing it from database",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
    }

    class task(val context: Context, val menuItem: menuitem, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {
        val cart_db = Room.databaseBuilder(context, database::class.java, "cart_db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when (mode) {
                1 -> {
                    val menu_item: menuitem =
                        cart_db.cartdao().get_menu_item_by_id(menuItem.menu_id.toString())
                    cart_db.close()
                    return menu_item != null
                }
                2 -> {
                    cart_db.cartdao().insert_menu_item(menuItem)
                    cart_db.close()
                    return true
                }
                3 -> {
                    cart_db.cartdao().delet_menu_item(menuItem)
                    cart_db.close()
                    return true

                }
            }
            return false
        }

    }


}