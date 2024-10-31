package com.kashif.yelpfinder.presenter.common

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
// Sealed class representing the connection status
sealed class ConnectionStatus {

    // Object representing the available connection status
    object Available : ConnectionStatus()

    // Object representing the unavailable connection status
    object Unavailable : ConnectionStatus()
}