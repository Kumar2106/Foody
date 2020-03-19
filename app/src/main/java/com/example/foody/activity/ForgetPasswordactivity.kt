package com.example.foody.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foody.R
import org.json.JSONObject

class ForgetPasswordactivity : AppCompatActivity() {
    lateinit var mobilenumber: EditText
    lateinit var email: EditText
    lateinit var nextbutton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        mobilenumber = findViewById(R.id.forgetmobile)
        email = findViewById(R.id.forgetemail)
        nextbutton = findViewById(R.id.forgetbutton)


        nextbutton.setOnClickListener(View.OnClickListener {
            jsonrequest()
        })
    }

    private fun jsonrequest() {
        var mobilenumber = mobilenumber.text.toString()
        var email = email.text.toString()

        var url = "http://13.235.250.119/v2/forgot_password/fetch_result"
        var param = JSONObject()
        param.put("mobile_number", mobilenumber)
        param.put("email", email)

        var queue = Volley.newRequestQueue(this)
        var JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.POST,
            url,
            param,
            Response.Listener {
                var data = it.getJSONObject("data")
                var success = data.getBoolean("success")
                if (success) {
                    val intent = Intent(this,otp::class.java)
                    intent.putExtra("mobile_number",mobilenumber)
                    startActivity(intent)
                }
            },
            Response.ErrorListener { }) {
            override fun getHeaders(): MutableMap<String, String> {
                val header = HashMap<String, String>()
                header["Content-type"] = "application/json"
                header["token"] = "c99d07abe51428"
                return header
            }
        }
        queue.add(JsonObjectRequest)

    }
}
