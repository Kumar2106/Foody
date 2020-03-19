package com.example.foody.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cart_database.menuitem
import com.example.foody.R

class cart_adapter(val context: Context, val cart_list: List<menuitem>) :
    RecyclerView.Adapter<cart_adapter.cart_adapter_view>() {

    class cart_adapter_view(view: View) : RecyclerView.ViewHolder(view) {
        val cart_item_name: TextView = view.findViewById(R.id.cart_item_name)
        val cart_item_cost: TextView = view.findViewById(R.id.cart_item_cost)
    }


    override fun getItemCount(): Int {
        return cart_list.size
    }


    override fun onBindViewHolder(holder: cart_adapter_view, position: Int) {
        val menu_item = cart_list[position]
        holder.cart_item_name.text = menu_item.menu_item_name
        holder.cart_item_cost.append(menu_item.menu_item_cost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cart_adapter_view {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_menu_item, parent, false)
        return cart_adapter_view(view)
    }

}