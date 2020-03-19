package com.example.foody.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foody.R
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    //loginrestration is the textview used to for registration textview
    //loginforget is the textview used for login textview
    lateinit var loginrestriation: TextView
    lateinit var loginforget: TextView
    lateinit var loginbutton: Button
    lateinit var loginnumber: EditText
    lateinit var loginpassword: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginrestriation = findViewById(R.id.loginregister)
        loginforget = findViewById(R.id.loginforget)
        loginbutton = findViewById(R.id.loginbutton)
        loginnumber = findViewById(R.id.loginnumber)
        loginpassword = findViewById(R.id.loginpassword)

        loginforget.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ForgetPasswordactivity::class.java))

        })

        loginrestriation.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, Registartion_activity::class.java))

        })

        loginbutton.setOnClickListener(View.OnClickListener {
            jsonrequest()
        })


    }

    fun jsonrequest(){
        var number = loginnumber.text.toString()
        var password = loginpassword.text.toString()
        var url = "http://13.235.250.119/v2/login/fetch_result"
        var queue = Volley.newRequestQueue(this)
        var param = JSONObject()
        param.put("mobile_number", number)
        param.put("password", password)

        var jsonObjectRequest = object :JsonObjectRequest(
            Request.Method.POST,
            url,
            param,
            Response.Listener {
                var data = it.getJSONObject("data")
                var success = data.getBoolean("success")
                if (success){

                    var data = data.getJSONObject("data")
                    var name = data.getString("name")
                    var mobilenumber = data.getString("mobile_number")
                    var email  = data.getString("email")
                    var address = data.getString("address")


                    var userdetail = getSharedPreferences("userdetail", Context.MODE_PRIVATE)
                    var userinfo:SharedPreferences.Editor = userdetail.edit()
                    userinfo.putString("name",data.getString("name"))
                    userinfo.putString("mobile_number",mobilenumber)
                    userinfo.putString("email",email)
                    userinfo.putString("address",address)
                    userinfo.putString("user_id",data.getString("user_id"))
                    userinfo.putBoolean("islogged",true)
                    userinfo.commit()
                    startActivity(Intent(this,MainActivity::class.java))
                }
                else{
                    Toast.makeText(this,"Invalid phone or Password",Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener {
                Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()

            }){
            override fun getHeaders(): MutableMap<String, String> {
                val header = HashMap<String,String>()
                header["Content-type"] = "application/json"
                header["token"] = "c99d07abe51428"
                return header

            }
        }


        queue.add(jsonObjectRequest)

    }


    }

