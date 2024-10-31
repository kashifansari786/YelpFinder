package com.kashif.yelpfinder.presenter.yelpNavigator

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
@HiltViewModel // Marks this class as a ViewModel that will be provided by Hilt
class YelpNavigatorModel @Inject constructor() : ViewModel() { // Constructor with dependency injection

    // Function to update internet connectivity status
    fun updateInternetConnectivity(isConnected: Boolean): Boolean {
        return isConnected // Returns the current connectivity status
    }
}