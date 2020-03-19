package com.example.foody.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.Gravity.*
import androidx.fragment.app.Fragment
import android.widget.TextView

import com.example.foody.R

/**
 * A simple [Fragment] subclass.
 */
class my_profile : Fragment() {
    lateinit var username :TextView
    lateinit var usermobilenumber:TextView
    lateinit var useremail:TextView
    lateinit var address:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_profile,container,false)
        username = view.findViewById(R.id.usernmae)
        usermobilenumber = view.findViewById(R.id.usermobilenumber)
        useremail = view.findViewById(R.id.useremail)
        address = view.findViewById(R.id.useraddress)

        var userdetail = activity?.getSharedPreferences("userdetail",Context.MODE_PRIVATE)
        username.text = userdetail?.getString("name","name?")
        usermobilenumber.text = userdetail?.getString("mobile_number","mobile?")
        useremail.text = userdetail?.getString("email","email?")
        address.text = userdetail?.getString("address","address?")
        return view
    }

}
