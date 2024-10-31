package com.kashif.yelpfinder.presenter.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
// Extension property to get the current connectivity status from the context
val Context.currentConnectivityStatus: ConnectionStatus
    get() {
        // Get the ConnectivityManager service
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // Return the current connectivity status
        return getCurrentConnectivityStatus(connectivityManager)
    }

// Function to determine the current connectivity status based on the ConnectivityManager
private fun getCurrentConnectivityStatus(connectivityManager: ConnectivityManager): ConnectionStatus {
    // Check if any network is connected and has internet capability
    val connected = connectivityManager.allNetworks.any { network ->
        connectivityManager.getNetworkCapabilities(network)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }
    // Return the appropriate connection status
    return if (connected) ConnectionStatus.Available else ConnectionStatus.Unavailable
}

// Function to observe connection status changes as a Flow
@RequiresApi(Build.VERSION_CODES.S)
fun Context.observeConnectionAsFlow() = callbackFlow {
    // Get the ConnectivityManager service
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Create a callback to handle connectivity changes
    val callback = NetworkCallback { connectionState ->
        trySend(connectionState) // Send the new connection state
    }

    // Build a network request to listen for internet capability
    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    // Register the network callback with the ConnectivityManager
    connectivityManager.registerNetworkCallback(networkRequest, callback)

    // Get the current connectivity status and send it
    val currentState = getCurrentConnectivityStatus(connectivityManager = connectivityManager)
    trySend(currentState)

    // Unregister the network callback when the flow is closed
    awaitClose {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}

// Helper function to create a NetworkCallback for handling connectivity changes
fun NetworkCallback(callback: (ConnectionStatus) -> Unit): ConnectivityManager.NetworkCallback {
    return object : ConnectivityManager.NetworkCallback() {
        // Called when a network becomes available
        override fun onAvailable(network: Network) {
            callback(ConnectionStatus.Available)
        }

        // Called when a network is lost
        override fun onLost(network: Network) {
            callback(ConnectionStatus.Unavailable)
        }
    }
}