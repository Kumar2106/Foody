package com.example.foody.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectionManager {
    fun checkconnectivity(context: Context): Boolean {
        val coonectionmanager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = coonectionmanager.activeNetworkInfo
        if (activeNetwork != null) {
            return activeNetwork.isConnected

        } else {
            return false
        }

    }
}