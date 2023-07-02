package com.example.paging3sampleapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class NetworkStateManager(context: Context) {
    private val _networkState = MutableStateFlow(NetworkState.Unavailable)
    val networkState: StateFlow<NetworkState> get() = _networkState

    private val connectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Log.e("test", "a available")
            _networkState.update { NetworkState.Available }
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            Log.e("test", "a losing")
            _networkState.update { NetworkState.Unavailable }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Log.e("test", "a lost")
            _networkState.update { NetworkState.Unavailable }
        }

        override fun onUnavailable() {
            super.onUnavailable()
            Log.e("test", "a unavailable")
            _networkState.update { NetworkState.Unavailable }
        }
    }

    fun register() {
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    fun unregister() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    enum class NetworkState { Available, Unavailable }
}