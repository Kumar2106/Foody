package com.example.foody.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foody.R
import com.example.foody.adapter.resmenuadapter
import com.example.foody.model.menu_item
import com.example.foody.util.ConnectionManager
import org.json.JSONException

class resdetails : AppCompatActivity() {
    lateinit var resdetails: RecyclerView
    lateinit var toolbar: Toolbar
    lateinit var btncart: Button
    lateinit var resadapter: resmenuadapter
    lateinit var layoutManager: LinearLayoutManager
    val menulist = ArrayList<menu_item>()
//    var view = layoutInflater.inflate(R.layout.menu_list,parent,false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resdetails)
        resdetails = findViewById(R.id.resmenurecyclerview)
        toolbar = findViewById(R.id.resmenutoolbar)
        btncart = findViewById(R.id.btncart)


        setSupportActionBar(toolbar)
        supportActionBar?.title = intent.getStringExtra("resname")
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setIcon(R.drawable.registration_toolbar_bacl)
        if (ConnectionManager().checkconnectivity(this)) {
            val request = Volley.newRequestQueue(this)
            var baseurl: String = "http://13.235.250.119/v2/restaurants/fetch_result/"
            baseurl += intent.getStringExtra("resid")

            try {
                val jsonRequest = object : JsonObjectRequest(
                    Request.Method.GET,
                    baseurl,
                    null,
                    Response.Listener {
                        val jsonObjectRequest = it.getJSONObject("data")
                        val success = jsonObjectRequest.getBoolean("success")
                        if (success) {
                            val menuarray = jsonObjectRequest.getJSONArray("data")
                            for (i in 0 until menuarray.length()) {
                                val menu = menuarray.getJSONObject(i)
                                val resmenu = menu_item(
                                    menu.getString("id"),
                                    menu.getString("name"),
                                    menu.getString("cost_for_one"),
                                    menu.getString("restaurant_id")
                                )
                                menulist.add(resmenu)
                            }
                        }
                        resadapter = resmenuadapter(this, menulist)
                        layoutManager = LinearLayoutManager(this)
                        resdetails.adapter = resadapter
                        resdetails.layoutManager = layoutManager

                    },
                    Response.ErrorListener {
                        Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                    }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val header = HashMap<String, String>()
                        header["Content-type"] = "application/json"
                        header["token"] = "c99d07abe51428"
                        return header
                    }

                }
                request.add(jsonRequest)


            } catch (e: JSONException) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()

            }


        } else {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("No Internert")
            dialog.setMessage("PHone is not connected to the internet.")
            dialog.setPositiveButton("Open Setting") { txt, listener ->
                startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))

            }
            dialog.setNegativeButton("Close App") { txt, listener ->
                finish()
            }
            dialog.create()
            dialog.show()

        }

        btncart.setOnClickListener(View.OnClickListener {
            val intent1 = Intent(Intent(this, cart::class.java))
            intent1.putExtra("res_id",intent.getStringExtra("resid"))
            intent1.putExtra("resname", intent.getStringExtra("resname"))
            startActivity(intent1)
        })


    }
}
