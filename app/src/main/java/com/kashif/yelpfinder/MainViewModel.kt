package com.kashif.yelpfinder

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kashif.yelpfinder.data.manager.PreferenceClass
import com.kashif.yelpfinder.domain.useCases.app_entry.AppEntryUseCases
import com.kashif.yelpfinder.presenter.navGraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
* Created by Mohammad Kashif Ansari on 30,October,2024
*/
@HiltViewModel // Indicates that Hilt will provide dependencies for this ViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases, // Use case for app entry logic
    private val preferenceClass: PreferenceClass // Class for managing preferences
) : ViewModel() {

    var splashCondition by mutableStateOf(true) // State for splash screen visibility
        private set // Only allow setting from within this class

    var startDestination by mutableStateOf(Route.AppStartNavigation.route) // State for the start destination of navigation
        private set // Only allow setting from within this class

    init { // Initialization block
        appEntryUseCases.readAppEntry().onEach { shouldStartFromHomeScreen -> // Observe app entry preference
            if (shouldStartFromHomeScreen) // Check if the user should start from the home screen
                startDestination = Route.YelpNavigation.route // Set start destination to Yelp navigation
            else
                startDestination = Route.AppStartNavigation.route // Set start destination to app start navigation

            delay(300) // Delay for 300 milliseconds (for splash screen display)
            splashCondition = false // Set splash condition to false to hide the splash screen
        }.launchIn(viewModelScope) // Launch the flow in the ViewModel's coroutine scope
    }

    // Function to save the user's location
    fun saveLocation(latitude: Double, longitude: Double) {
        Log.d("inside_main", "location:- " + latitude + ", " + longitude) // Log the location
        viewModelScope.launch { // Launch a coroutine in the ViewModel's scope
            preferenceClass.saveLocation(latitude, longitude) // Save the location using the preference class
        }
    }
}