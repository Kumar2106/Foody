package com.example.foody.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foody.R
import com.example.foody.util.ConnectionManager
import org.json.JSONException
import org.json.JSONObject

class Registartion_activity : AppCompatActivity() {
    lateinit var registrationtoolbar: Toolbar
    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var mobilenumber: EditText
    lateinit var deliveraddress: EditText
    lateinit var password: EditText
    lateinit var confirmpassword: EditText
    lateinit var registerbutton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registartion_activity)


        registrationtoolbar = findViewById(R.id.registrationtoolbar)
        name = findViewById(R.id.txtregistername)
        email = findViewById(R.id.txtregisteremail)
        mobilenumber = findViewById(R.id.txtregisterphone)
        deliveraddress = findViewById(R.id.txtregiterdelivery)
        password = findViewById(R.id.txtregisterpasword)
        confirmpassword = findViewById(R.id.txtregisterconfirmpassword)
        registerbutton = findViewById(R.id.buttonregister)




        setSupportActionBar(registrationtoolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.registration_toolbar_bacl)

        registerbutton.setOnClickListener(View.OnClickListener {
            var name = name.text.toString()
            var email = email.text.toString()
            var mobilenumber = mobilenumber.text.toString()
            var deliveryaddress = deliveraddress.text.toString()
            var password = password.text.toString()
            var confirmpassowrd = confirmpassword.text.toString()
            if (name.isNullOrEmpty()
                && email.isNullOrEmpty()
                && mobilenumber.isNullOrEmpty()
                && deliveryaddress.isNullOrEmpty()
                && password.isNullOrEmpty() &&
                confirmpassowrd.isNullOrEmpty()
            ) {
                Toast.makeText(this, "Please fill the empty block", Toast.LENGTH_LONG).show()
            } else {

                if (password == confirmpassowrd) {
                    if (ConnectionManager().checkconnectivity(this)) {
                        val jsonobject = JSONObject()
                        jsonobject.put("name", name)
                        jsonobject.put("mobile_number", mobilenumber)
                        jsonobject.put("password", password)
                        jsonobject.put("address", deliveryaddress)
                        jsonobject.put("email", email)
                        val url = "http://13.235.250.119/v2/register/fetch_result"
                        val quene = Volley.newRequestQueue(this)
                        val jsonRequest = object : JsonObjectRequest(
                            Request.Method.POST,
                            url,
                            jsonobject,
                            Response.Listener {
                                try {

                                    val data = it.getJSONObject("data")
                                    val success = data.getBoolean("success")
                                    if (success){
                                        Toast.makeText(this,"You are now registered",Toast.LENGTH_LONG).show()
                                        startActivity(Intent(this,LoginActivity::class.java))
                                    }
                                    else{
                                        Toast.makeText(this,"you are already registered",Toast.LENGTH_LONG).show()
                                        startActivity(Intent(this,LoginActivity::class.java))
                                    }

                                } catch (e: JSONException) {
                                    Toast.makeText(
                                        this,
                                        "There were error while fetching the data",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            },
                            Response.ErrorListener {
                                Toast.makeText(
                                    this,
                                    it.toString(),
                                    Toast.LENGTH_LONG
                                ).show()

                            })
                        {
                            override fun getHeaders(): MutableMap<String, String> {
                                val header = HashMap<String, String>()
                                header["Content-type"] = "application/json"
                                header["token"] = "c99d07abe51428"
                                return header
                            }

                        }
                        quene.add(jsonRequest)



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

                } else {
                    Toast.makeText(
                        this,
                        "confirm password is not matching with the password",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }


        )

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
