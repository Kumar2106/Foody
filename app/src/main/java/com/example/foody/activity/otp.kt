package com.example.foody.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foody.R
import org.json.JSONException
import org.json.JSONObject

class otp : AppCompatActivity() {
    lateinit var otp: EditText
    lateinit var otptoolbar: Toolbar
    lateinit var newpassword: EditText
    lateinit var confirmpassword: EditText
    lateinit var otpsubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        otp = findViewById(R.id.otp)
        otptoolbar = findViewById(R.id.otptoolbar)
        newpassword = findViewById(R.id.otpnewpassord)
        confirmpassword = findViewById(R.id.otpconfirmpassword)
        otpsubmit = findViewById(R.id.otpsubmitbutton)


        otpsubmit.setOnClickListener(View.OnClickListener {
            jsonrequest()
        })
    }

    fun jsonrequest() {
        var otp = otp.text.toString()
        var newpassword = newpassword.text.toString()
        var confirmpassword = confirmpassword.text.toString()
        if (otp.isNullOrEmpty() && newpassword.isNullOrEmpty() && confirmpassword.isNullOrEmpty()) {
            Toast.makeText(this, "Field must be filled properly", Toast.LENGTH_LONG).show()
        } else {
            if (newpassword == confirmpassword) {
                val param = JSONObject()
                param.put("mobile_number", intent.getStringExtra("mobile_number"))
                param.put("password", confirmpassword)
                param.put("otp", otp)
                val url = "http://13.235.250.119/v2/reset_password/fetch_result"

                val request = Volley.newRequestQueue(this)
                try {
                    val jsonObjectRequest = object : JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        param,
                        Response.Listener {
                            val data = it.getJSONObject("data")
                            val success = data.getBoolean("success")
                            if (success) {
                                Toast.makeText(
                                    this,
                                    data.getString("successMessage"),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        },
                        Response.ErrorListener {
                            Toast.makeText(
                                this,
                                "There was an error while making a request",
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
                    request.add(jsonObjectRequest)
                } catch (e: JSONException) {
                    Toast.makeText(this, "There was some exception in Json", Toast.LENGTH_LONG)
                        .show()

                }
            }
        }
    }
}
