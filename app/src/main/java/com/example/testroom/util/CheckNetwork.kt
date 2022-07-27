package com.example.testroom.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData

class CheckNetwork(private val connectivityManager: ConnectivityManager): LiveData<NetworkState>() {
    constructor(application: Application):this(application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
    init {
        if(Build.VERSION.SDK_INT>= 23){
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    }
                    else ->{postValue(NetworkState.Failed)}
                }
            }else{
                postValue(NetworkState.Failed)
            }
        }else{
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if ((activeNetworkInfo == null) || !activeNetworkInfo.isConnected) {
                postValue(NetworkState.Failed)
            }
        }
    }
    val networkCallback = object :ConnectivityManager.NetworkCallback(){
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Log.d("TAG", "onAvailable: ")
            postValue(NetworkState.Connected)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Log.d("TAG", "onLost: ")
            postValue(NetworkState.Failed)
        }
    }
    override fun onActive() {
        super.onActive()
        val request = NetworkRequest.Builder()
        connectivityManager.registerNetworkCallback(request.build() , networkCallback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}