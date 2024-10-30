package com.farzin.core_common

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build

object CheckConnection {


    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (connectivityManager != null) {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                networkCapabilities != null &&
                        (networkCapabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_WIFI) ||
                                networkCapabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_CELLULAR))

            } else {
                val networkInfo = connectivityManager.activeNetworkInfo
                networkInfo != null && networkInfo.isConnected
            }
        }

        return false
    }


}