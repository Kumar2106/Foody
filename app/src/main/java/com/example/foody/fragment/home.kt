package com.example.foody.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foody.R
import com.example.foody.adapter.homeadapter
import com.example.foody.model.restaurants
import com.example.foody.util.ConnectionManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.restaurants_detail.*
import org.json.JSONException

/**
 * A simple [Fragment] subclass.
 */
class home : Fragment() {
    lateinit var layoutManager: LinearLayoutManager
    lateinit var homrerecycler: RecyclerView
    lateinit var homeadapter: homeadapter
    var resturantslists = ArrayList<restaurants>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        homrerecycler = view.findViewById(R.id.homerecycler)
        if (ConnectionManager().checkconnectivity(activity as Context)) {
            val requestQueue = Volley.newRequestQueue(activity as Context)
            val url = "http://13.235.250.119/v2/restaurants/fetch_result/"
            try {
                val jsonObjectRequest = object : JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    Response.Listener {
                        val data = it.getJSONObject("data")
                        val success = data.getBoolean("success")
                        if (success) {
                            val jsonobject = data.getJSONArray("data")
                            for (i in 0 until jsonobject.length()) {
                                var resobject = jsonobject.getJSONObject(i)
                                var reslits = restaurants(
                                    resobject.getString("id"),
                                    resobject.getString("name"),
                                    resobject.getString("rating"),
                                    resobject.getString("cost_for_one"),
                                    resobject.getString("image_url")
                                )
                                resturantslists.add(reslits)
                            }

                        }
                        layoutManager = LinearLayoutManager(activity as Context)
                        homeadapter = homeadapter(activity as Context,resturantslists)
                        homrerecycler.layoutManager = layoutManager
                        homrerecycler.adapter = homeadapter

                    },
                    Response.ErrorListener {
                        Toast.makeText(activity as Context, it.toString(), Toast.LENGTH_LONG).show()
                    }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val header = HashMap<String, String>()
                        header["Content-type"] = "application/json"
                        header["token"] = "c99d07abe51428"
                        return header
                    }
                }


                requestQueue.add(jsonObjectRequest)
            } catch (e: JSONException) {

                Toast.makeText(
                    activity as Context,
                    "There was an exception while making Json request",
                    Toast.LENGTH_LONG
                ).show()
            }



        } else {
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("No Internert")
            dialog.setMessage("PHone is not connected to the internet.")
            dialog.setPositiveButton("Open Setting") { txt, listener ->
                startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))

            }
            dialog.setNegativeButton("Close App") { txt, listener ->
                activity?.finish()
            }
            dialog.create()
            dialog.show()

        }

        return view
    }

}
