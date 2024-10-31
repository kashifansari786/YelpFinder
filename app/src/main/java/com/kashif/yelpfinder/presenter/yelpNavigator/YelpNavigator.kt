package com.kashif.yelpfinder.presenter.yelpNavigator

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.LOG_TAG
import com.kashif.yelpfinder.R
import com.kashif.yelpfinder.domain.model.SearchModel
import com.kashif.yelpfinder.presenter.bookmark.BookmarkScreen
import com.kashif.yelpfinder.presenter.bookmark.BookmarkViewModel
import com.kashif.yelpfinder.presenter.navGraph.Route
import com.kashif.yelpfinder.presenter.search.SearchEvent
import com.kashif.yelpfinder.presenter.search.SearchScreen
import com.kashif.yelpfinder.presenter.search.SearchViewModel
import com.kashif.yelpfinder.presenter.yelpNavigator.component.BottomNavigationItems
import com.kashif.yelpfinder.presenter.yelpNavigator.component.YelpBottomNavigation

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
@Composable // Marks this function as a composable function
fun YelpNavigator(updateInternetConnectivity: Boolean) {

    val context = LocalContext.current // Get the current context
    // Define a list of bottom navigation items and remember it across recompositions
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItems(icon = R.drawable.ic_search, text = "Search"), // Search item
            BottomNavigationItems(icon = R.drawable.ic_bookmark, text = "Bookmark") // Bookmark item
            // BottomNavigationItems(icon = R.drawable.ic_home, text = "Home") // Home item (commented out)
        )
    }
    val navController = rememberNavController() // Remember the NavController for navigation
    val backstackState = navController.currentBackStackEntryAsState().value // Get the current back stack entry
    var selectedItem by rememberSaveable {
        mutableStateOf(0) // Initialize selected item index
    }
    // Update selectedItem based on the current back stack destination
    selectedItem = remember(key1 = backstackState) {
        when (backstackState?.destination?.route) {
            Route.SearchScreen.route -> 0 // If on SearchScreen, select it
            Route.BookmarkScreen.route -> 1 // If on BookmarkScreen, select it
            // Route.BookmarkScreen.route -> 2 // Commented out
            else -> 0 // Default to SearchScreen
        }
    }

    // Determine if the bottom navigation bar should be visible
    val isBottomBarVisible = remember(key1 = backstackState) {
        backstackState?.destination?.route == Route.SearchScreen.route ||
                backstackState?.destination?.route == Route.BookmarkScreen.route
    }

    // Scaffold provides a layout structure with a bottom bar
    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = { // Define the bottom bar
            if (isBottomBarVisible) { // Show bottom bar only if visible
                YelpBottomNavigation(
                    items = bottomNavigationItems, // Pass the navigation items
                    selected = selectedItem, // Pass the selected item index
                    onItemClick = { index -> // Handle item clicks
                        when (index) {
                            0 -> navigateToTop(
                                navController = navController,
                                route = Route.SearchScreen.route // Navigate to SearchScreen
                            )
                            1 -> navigateToTop(
                                navController = navController,
                                route = Route.BookmarkScreen.route // Navigate to BookmarkScreen
                            )
                            // 2 -> navigateToTop( // Commented out
                            //     navController = navController,
                            //     route = Route.BookmarkScreen.route
                            // )
                        }
                    })
            }
        }) { // Content of the Scaffold
        val bottomPadding = it.calculateBottomPadding() // Calculate bottom padding for the content
        NavHost(
            navController = navController,
            startDestination = Route.SearchScreen.route, // Set the starting destination
            modifier = Modifier.padding(bottom = bottomPadding) // Apply bottom padding
        ) {
            composable(route = Route.SearchScreen.route) { // Define the composable for SearchScreen
                val viewModel: SearchViewModel = hiltViewModel() // Get the SearchViewModel
                val state = viewModel.state.value // Get the current state from the ViewModel
                // Show a toast if there is a side effect
                if (viewModel.sideEffect != null) {
                    Toast.makeText(context, viewModel.sideEffect, Toast.LENGTH_SHORT).show() // Show toast
                    viewModel.onEvent(SearchEvent.RemoveSideEffect) // Remove side effect
                }
                // Render the SearchScreen with the current state and event handling
                SearchScreen(state = state, event = viewModel::onEvent,viewModel=viewModel, navigationToDetails = {
                    // navigateToDetails(navController, data = it) // Placeholder for navigation
                }, updateInternetConnectivity)
            }
            composable(route = Route.BookmarkScreen.route) { // Define the composable for BookmarkScreen
                val viewModel: BookmarkViewModel = hiltViewModel() // Get the BookmarkViewModel
                val searchViewModel: SearchViewModel = hiltViewModel() // Get the SearchViewModel
                val state = viewModel.state.value // Get the current state from the ViewModel
                Log.d("inside_navigator","state:- "+state.article.size)
                // Render the BookmarkScreen with state and navigation handling
                BookmarkScreen(state = state, event = searchViewModel::onEvent,viewModel=searchViewModel,navigationToDetails = { article ->
                    // navigateToDetails(navController, article) // Placeholder for navigation
                })
            }
        }
    }
}

// Function to navigate to the top of the navigation graph
private fun navigateToTop(navController: NavController, route: String) {
    navController.navigate(route) { // Navigate to the specified route
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) { // Pop up to the start destination
                saveState = true // Save the state
            }
            restoreState = true // Restore the state
            launchSingleTop = true // Launch a single top instance
        }
    }
}

// Function to navigate to the details of an article
private fun navigateToDetails(
    navController: NavController,
    article: SearchModel // The article to pass
) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article) // Save article to state handle
    // navController.navigate(route = Route.DetailsScreen.route) // Placeholder for navigation to details screen
}