package com.example.foody.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.foody.R

class WelcomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_screen)
        Handler().postDelayed({
            var userdetails = getSharedPreferences("userdetail",Context.MODE_PRIVATE)
            var islogged = userdetails.getBoolean("islogged",false)

            if (islogged){
                startActivity(Intent(this,MainActivity::class.java))
            }
            else{
                startActivity(Intent(this,LoginActivity::class.java))
            }
        },2000)

    }

    override fun onResume() {
        Handler().postDelayed({
            var userdetails = getSharedPreferences("userdetail",Context.MODE_PRIVATE)
            var islogged = userdetails.getBoolean("islogged",false)

            if (islogged){
                startActivity(Intent(this,MainActivity::class.java))
            }
            else{
                startActivity(Intent(this,LoginActivity::class.java))
            }
        },2000)
        super.onResume()


    }

}
