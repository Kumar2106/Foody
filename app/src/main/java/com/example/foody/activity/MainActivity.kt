package com.example.foody.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.foody.R
import com.example.foody.fragment.*
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var name: TextView
    lateinit var mobilenumber: TextView
    lateinit var Navigationview: NavigationView
    lateinit var toolbar: Toolbar
    var previousitme: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Navigationview = findViewById(R.id.mainactivitynavigation)
        var navigationheader = Navigationview.getHeaderView(0)
        var menu = Navigationview.menu
        previousitme = menu.findItem(R.id.Home)
        toolbar = findViewById(R.id.mainactivitytoolbar)
        name = navigationheader.findViewById(R.id.mainactivityname)
        mobilenumber = navigationheader.findViewById(R.id.mainactivitynumber)
        drawerLayout = findViewById(R.id.drawerlayout)

        setuptoolbar()
        openhome()
        val actionbar =
            ActionBarDrawerToggle(
                this@MainActivity,
                drawerLayout,
                R.string.opendrawer,
                R.string.closedrawer
            )
        drawerLayout.addDrawerListener(actionbar)
        actionbar.syncState()

        var userdetails = getSharedPreferences("userdetail",Context.MODE_PRIVATE)
        var username = userdetails.getString("name","name?")
        var usernumber = userdetails.getString("mobile_number","mobile?")
        name.text = username
        mobilenumber.text = usernumber


        Navigationview.setNavigationItemSelectedListener {
            if (previousitme != null) {
                previousitme?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousitme = it
            when (it.itemId) {
                R.id.Home -> {
                    openhome()
                    drawerLayout.closeDrawers()
                }
                R.id.MyProfile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, my_profile())
                        .addToBackStack("My Profile")
                        .commit()
                    supportActionBar?.title = "My Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.FavoriteRestaurants -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, favorite_restaurants())
                        .addToBackStack("Favorite Restaurants")
                        .commit()
                    supportActionBar?.title ="Favorite Restaurants"
                    drawerLayout.closeDrawers()
                }
                R.id.OrderHIstory -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, oreder_histodry())
                        .addToBackStack("Order History")
                        .commit()
                    supportActionBar?.title="Order History"
                    drawerLayout.closeDrawers()
                }
                R.id.Faq -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, faq())
                        .addToBackStack("Faq")
                        .commit()
                    supportActionBar?.title = "Frequently Asked Questions"
                    drawerLayout.closeDrawers()
                }
                R.id.Logout -> {
                    var userdetailsedit = userdetails.edit()
                    userdetailsedit.putBoolean("islogged",false)
                    userdetailsedit.commit()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }


            }

            return@setNavigationItemSelectedListener true
        }
    }

    fun setuptoolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Foody"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//TODO: Home button is not responding.


    }

    fun openhome() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, home())
            .addToBackStack("Home")
            .commit()
        supportActionBar?.title = "Home"
        previousitme?.isChecked = true

    }

}
