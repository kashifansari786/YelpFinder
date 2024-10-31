package com.kashif.yelpfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kashif.yelpfinder.presenter.navGraph.NavGraph
import com.kashif.yelpfinder.ui.theme.YelpFinderTheme
import com.kashif.yelpfinder.util.Dimens
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint // Indicates that Hilt will provide dependencies for this activity
class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainViewModel>() // Lazy initialization of the MainViewModel

    @RequiresApi(Build.VERSION_CODES.S) // Requires API level S or higher
    override fun onCreate(savedInstanceState: Bundle?) { // Override the onCreate method
        super.onCreate(savedInstanceState) // Call the superclass implementation
        WindowCompat.setDecorFitsSystemWindows(window, false) // Adjust window to fit system windows
        installSplashScreen().apply { // Install the splash screen
            setKeepOnScreenCondition { // Set condition to keep splash screen on
                viewModel.splashCondition // Check if splash condition is met
            }
        }

        setContent { // Set the content view
            var locationPermissionsGranted by remember { mutableStateOf(areLocationPermissionsAlreadyGranted()) } // Check if location permissions are granted
            var shouldShowPermissionRationale by remember { // State to show rationale for permissions
                mutableStateOf(
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) // Check if rationale should be shown
                )
            }

            var shouldDirectUserToApplicationSettings by remember { // State to determine if user should be directed to settings
                mutableStateOf(false)
            }

            var currentPermissionsStatus by remember { // State for current permissions status
                mutableStateOf(decideCurrentPermissionStatus(locationPermissionsGranted, shouldShowPermissionRationale)) // Determine initial status
            }

            val locationPermissions = arrayOf( // Array of location permissions
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

            // Launcher to request location permissions
            val locationPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = { permissions -> // Handle the result of the permission request
                    locationPermissionsGranted = permissions.values.reduce { acc, isPermissionGranted -> // Update permissions granted status
                        acc && isPermissionGranted
                    }

                    if (!locationPermissionsGranted) { // Check if permissions are not granted
                        shouldShowPermissionRationale =
                            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) // Update rationale state
                    }
                    shouldDirectUserToApplicationSettings = !shouldShowPermissionRationale && !locationPermissionsGranted // Update settings redirection state
                    currentPermissionsStatus = decideCurrentPermissionStatus(locationPermissionsGranted, shouldShowPermissionRationale) // Update current status
                }
            )

            if (currentPermissionsStatus == "Granted") { // If permissions are granted
                Log.d("inside_main", "permission:- true") // Log permission status
                GetCurrentLocation(viewModel) // Fetch current location
            }

            val lifecycleOwner = LocalLifecycleOwner.current // Get the current lifecycle owner
            // Observe lifecycle events to handle permission requests
            DisposableEffect(key1 = lifecycleOwner, effect = {
                val observer = LifecycleEventObserver { _, event -> // Create an observer for lifecycle events
                    if (event == Lifecycle.Event.ON_START && // Check if the event is ON_START
                        !locationPermissionsGranted && // Check if permissions are not granted
                        !shouldShowPermissionRationale) { // Check if rationale is not needed
                        locationPermissionLauncher.launch(locationPermissions) // Launch the permission request
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer) // Add the observer to the lifecycle
                onDispose { // Cleanup
                    lifecycleOwner.lifecycle.removeObserver(observer) // Remove observer when the composable is disposed
                }
            })

            val scope = rememberCoroutineScope() // Remember a coroutine scope
            val snackbarHostState = remember { SnackbarHostState() } // Create a SnackbarHostState

            YelpFinderTheme { // Apply the theme to the content
                Surface(
                    modifier = Modifier.fillMaxSize(), // Make the surface fill the available space
                    color = MaterialTheme.colorScheme.background // Set the background color
                ) {
                    Scaffold(snackbarHost = { // Set up the scaffold with a snackbar host
                        SnackbarHost(hostState = snackbarHostState) // Provide the SnackbarHostState
                    }) { contentPadding -> // Padding for the scaffold content
                        if (currentPermissionsStatus == "Granted") { // If permissions are granted
                            val isSystemDarkMode = isSystemInDarkTheme() // Check if the system is in dark mode
                            val systemController = rememberSystemUiController() // Remember the system UI controller
                            SideEffect { // Side effect to modify the status bar color
                                systemController.setStatusBarColor(
                                    color = Color.Transparent, // Set the status bar color to transparent
                                    darkIcons = !isSystemDarkMode // Set icons color based on the system theme
                                )
                            }
                            Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) { // Box for content
                                val startDestnation = viewModel.startDestination // Get the start destination from the view model
                                Log.d("inside_main", "startdestinatoon:- " + startDestnation) // Log the start destination
                                NavGraph(startDestination = startDestnation) // Setup the navigation graph
                            }
                        } else { // If permissions are not granted
                            Column(
                                modifier = Modifier.fillMaxSize(), // Make the column fill the available space
                                verticalArrangement = Arrangement.Center, // Center items vertically
                                horizontalAlignment = Alignment.CenterHorizontally // Center items horizontally
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(contentPadding) // Apply padding
                                        .fillMaxWidth(), // Make text fill the width
                                    text = "Location Permissions", // Text to display
                                    textAlign = TextAlign.Center // Center align the text
                                )
                                Spacer(modifier = Modifier.padding(Dimens.MediumPadding24)) // Spacer for vertical spacing
                                Text(
                                    modifier = Modifier
                                        .padding(contentPadding) // Apply padding
                                        .fillMaxWidth(), // Make text fill the width
                                    text = "Current Permission Status: $currentPermissionsStatus", // Display current permission status
                                    textAlign = TextAlign.Center, // Center align the text
                                    fontWeight = FontWeight.Bold // Make the text bold
                                )
                            }
                        }

                        // Show a snackbar if rationale is required
                        if (shouldShowPermissionRationale) {
                            LaunchedEffect(Unit) { // Launch a coroutine for side effects
                                scope.launch { // Launch the coroutine
                                    val userAction = snackbarHostState.showSnackbar( // Show the snackbar
                                        message = "Please authorize location permissions", // Snackbar message
                                        actionLabel = "Approve", // Snackbar action label
                                        duration = SnackbarDuration.Indefinite, // Snackbar duration
                                        withDismissAction = true // Allow dismissal
                                    )
                                    when (userAction) { // Handle the user's action
                                        SnackbarResult.ActionPerformed -> { // If action was performed
                                            shouldShowPermissionRationale = false // Reset rationale state
                                            locationPermissionLauncher.launch(locationPermissions) // Request permissions again
                                        }
                                        SnackbarResult.Dismissed -> { // If dismissed
                                            shouldShowPermissionRationale = false // Reset rationale state
                                        }
                                    }
                                }
                            }
                        }

                        // Direct user to application settings if needed
                        if (shouldDirectUserToApplicationSettings) {
                            openApplicationSettings() // Open application settings
                        }
                    }
                }
            }
        }
    }

    // Check if location permissions are already granted
    private fun areLocationPermissionsAlreadyGranted(): Boolean {
        return ContextCompat.checkSelfPermission( // Check for fine location permission
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED // Return true if permission is granted
    }

    // Open the application settings screen
    private fun openApplicationSettings() {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null)).also { // Create intent to open settings
            startActivity(it) // Start the activity
        }
    }

    // Determine the current permission status
    private fun decideCurrentPermissionStatus(locationPermissionsGranted: Boolean,
                                              shouldShowPermissionRationale: Boolean): String {
        return if (locationPermissionsGranted) "Granted" // Return "Granted" if permissions are granted
        else if (shouldShowPermissionRationale) "Rejected" // Return "Rejected" if rationale should be shown
        else "Denied" // Return "Denied" if permissions are denied
    }
}

// Composable function to get the current location
@Composable
@SuppressLint("MissingPermission") // Suppress lint warning for missing permission
fun GetCurrentLocation(viewModel: MainViewModel) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(LocalContext.current) // Get location client

    fusedLocationClient.lastLocation.addOnSuccessListener { location -> // Get the last known location
        if (location != null) { // Check if location is not null
            viewModel.saveLocation(latitude = location.latitude, longitude = location.longitude) // Save location to the view model
        } else {
            Log.d("inside_main", "unable to fetch location") // Log if unable to fetch location
        }
    }
}

