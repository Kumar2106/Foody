package com.example.foody.fragment

import android.content.Context
import android.icu.lang.UCharacter
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.foody.R
import com.example.foody.adapter.favourite_adapter
import com.example.foody.favourite_database.favourite_database
import com.example.foody.favourite_database.favourite_entity

/**
 * A simple [Fragment] subclass.
 */
class favorite_restaurants : Fragment() {
    lateinit var favourite_frag: RecyclerView
    lateinit var favourite_list: List<favourite_entity>
    lateinit var favourite_layout_manager: LinearLayoutManager
    lateinit var favouriteAdapter: favourite_adapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite_restaurants, container, false)
        val db_access = db_access(activity as Context).execute()
        favourite_list = db_access.get()
        favourite_frag = view.findViewById(R.id.favourite_recycler)
        favourite_layout_manager = LinearLayoutManager(context)
        favouriteAdapter = favourite_adapter(activity as Context, favourite_list)
        favourite_frag.adapter = favouriteAdapter
        favourite_frag.layoutManager = favourite_layout_manager
        val itemDecoration = DividerItemDecoration(context,LinearLayout.VERTICAL)
        favourite_frag.addItemDecoration(itemDecoration)
        favouriteAdapter.notifyDataSetChanged()
        return view
    }

    class db_access(val context: Context) : AsyncTask<Void, Void, List<favourite_entity>>() {
        override fun doInBackground(vararg params: Void?): List<favourite_entity> {
            val fv_db =
                Room.databaseBuilder(context, favourite_database::class.java, "favouite_db").build()
            return fv_db.favourite_Dao().get_all_favourite()
        }

    }

}
