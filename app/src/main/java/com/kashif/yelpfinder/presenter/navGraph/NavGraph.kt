package com.kashif.yelpfinder.presenter.navGraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.kashif.yelpfinder.presenter.common.ConnectionStatus
import com.kashif.yelpfinder.presenter.common.currentConnectivityStatus
import com.kashif.yelpfinder.presenter.common.observeConnectionAsFlow
import com.kashif.yelpfinder.presenter.onBoarding.OnBoardingScreen
import com.kashif.yelpfinder.presenter.onBoarding.OnBoardingViewModel
import com.kashif.yelpfinder.presenter.yelpNavigator.YelpNavigator
import com.kashif.yelpfinder.presenter.yelpNavigator.YelpNavigatorModel

/**
* Created by Mohammad Kashif Ansari on 30,October,2024
*/

@RequiresApi(Build.VERSION_CODES.S) // Specify that this composable requires API level S or higher
@Composable
fun NavGraph(startDestination: String) { // Function to define the navigation graph
    val navController = rememberNavController() // Create a NavController to manage navigation
    NavHost(navController = navController, startDestination = startDestination) { // Set up the NavHost
        navigation(route = Route.AppStartNavigation.route, startDestination = Route.OnBoardingScreen.route) { // Define a navigation graph for the onboarding screens
            composable(route = Route.OnBoardingScreen.route) { // Define the onboarding screen composable
                val viewModel: OnBoardingViewModel = hiltViewModel() // Obtain the ViewModel using Hilt
                OnBoardingScreen { // Display the OnBoardingScreen
                    viewModel.onEvent(it) // Pass events to the ViewModel
                }
            }
        }
        navigation(route = Route.YelpNavigation.route, startDestination = Route.YelpNavigatorScreen.route) { // Define a navigation graph for Yelp screens
            composable(route = Route.YelpNavigatorScreen.route) { // Define the YelpNavigatorScreen composable
                val viewModel: YelpNavigatorModel = hiltViewModel() // Obtain the ViewModel using Hilt
                // Call the YelpNavigator composable and pass the internet connectivity status
                YelpNavigator(viewModel.updateInternetConnectivity(checkConnectivityStatus()))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S) // Specify that this composable requires API level S or higher
@Composable
fun connectivityStatus(): State<ConnectionStatus> { // Function to observe the connectivity status
    val context = LocalContext.current // Get the current context
    return produceState(initialValue = context.currentConnectivityStatus) { // Create a state with the initial connectivity status
        context.observeConnectionAsFlow().collect { // Collect connectivity status changes as a flow
            value = it // Update the state value with the new connectivity status
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S) // Specify that this composable requires API level S or higher
@Composable
fun checkConnectivityStatus(): Boolean { // Function to check if the device is connected to the internet
    val connection by connectivityStatus() // Get the current connectivity status as a state
    val isConnected = connection === ConnectionStatus.Available // Check if the status is available
    return isConnected // Return true if connected, false otherwise
}