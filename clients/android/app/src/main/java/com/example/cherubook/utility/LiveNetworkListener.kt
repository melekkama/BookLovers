package com.example.cherubook.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object LiveNetworkListener {
    private fun getConnectionType(context: Context): Boolean {
        var connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager;
        connectivityManager.run {
            this.getNetworkCapabilities(this.activeNetwork)?.run {
                return  hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            }
        }
        return false;
    }

    fun isConnected() = getConnectionType(MainApplication.applicationContext())
}