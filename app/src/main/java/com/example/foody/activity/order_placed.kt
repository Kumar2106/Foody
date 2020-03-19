package com.example.foody.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.foody.R

class order_placed : AppCompatActivity() {
    lateinit var order_placed_ok_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_placed)
        order_placed_ok_btn = findViewById(R.id.order_placed_ok_btn)
        order_placed_ok_btn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        })

    }
}
