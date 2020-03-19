package com.example.foody.activity

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import cart_database.database
import cart_database.menuitem
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foody.R
import com.example.foody.adapter.cart_adapter
import com.example.foody.util.ConnectionManager
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class cart : AppCompatActivity() {
    lateinit var cart_toolbar: Toolbar
    lateinit var ordering_from: TextView
    lateinit var cart_recycler: RecyclerView
    lateinit var cart_layout_manager: LinearLayoutManager
    lateinit var btn_place_order: Button
    lateinit var cart_menu_list: List<menuitem>
    var jsonArray = JSONArray()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        cart_toolbar = findViewById(R.id.cart_toolbar)
        ordering_from = findViewById(R.id.ordering_from)
        cart_recycler = findViewById(R.id.cart_list)
        btn_place_order = findViewById(R.id.btn_place_order)



        ordering_from.append(" " + intent.getStringExtra("resname"))
        val cart_db_access = db_access(this).execute()
        cart_menu_list = cart_db_access.get()

        var sum = 0
        for (i in cart_menu_list) {
            sum += i.menu_item_cost.toInt()
            val Jsonobject = JSONObject()
            Jsonobject.put(i.menu_id, i.menu_item_cost)
            jsonArray.put(Jsonobject)
        }
        Log.i("sum", sum.toString())
        btn_place_order.text = "Place Order($sum)"

        val cart_adapter = cart_adapter(this, cart_menu_list)
        cart_layout_manager = LinearLayoutManager(this)
        cart_recycler.adapter = cart_adapter
        cart_recycler.layoutManager = cart_layout_manager

        btn_place_order.setOnClickListener(View.OnClickListener {
            if (ConnectionManager().checkconnectivity(this)) {
                val url = "http://13.235.250.119/v2/place_order/fetch_result/"
                val request = Volley.newRequestQueue(this)
                val userdetails = getSharedPreferences("userdetail", Context.MODE_PRIVATE)
                val param = JSONObject()
                param.put("user_id", userdetails.getString("user_id", "no_id"))
                param.put("restaurant_id", intent.getStringExtra("res_id"))
                param.put("total_cost", sum)
                param.put("food", jsonArray)
                try {
                    val JsonObjectRequest = object : JsonObjectRequest(
                        Request.Method.POST,
                        url, param,
                        Response.Listener {
                            val data = it.getJSONObject("data")
                            val success = data.getBoolean("success")
                            if (success) {
                                val db_delete = db_delet(this)
                                db_delete.execute()

                                startActivity(Intent(this, order_placed::class.java))
                                Toast.makeText(this, "request was successful", Toast.LENGTH_LONG)
                                    .show()
                            }
                        },
                        Response.ErrorListener {
                            Toast.makeText(
                                this,
                                "There was error while making reques",
                                Toast.LENGTH_LONG
                            ).show()
                        }) {
                        override fun getHeaders(): MutableMap<String, String> {
                            val header = HashMap<String, String>()
                            header["Content-type"] = "application/json"
                            header["token"] = "c99d07abe51428"
                            return header
                        }
                    }
                    request.add(JsonObjectRequest)

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
        })


    }

    class db_access(val context: Context) : AsyncTask<Void, Void, List<menuitem>>() {
        override fun doInBackground(vararg params: Void?): List<menuitem> {
            val cart_db = Room.databaseBuilder(context, database::class.java, "cart_db").build()
            val cart_menu_list = cart_db.cartdao().get_all_menu_item()
            return cart_menu_list
        }
    }

    class db_delet(val context: Context) : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            val cart_db = Room.databaseBuilder(context, database::class.java, "cart_db").build()
            cart_db.cartdao().deletall()
            return true
        }

    }
}
