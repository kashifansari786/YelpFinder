package com.kashif.yelpfinder.presenter.navGraph

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
// Sealed class to represent different navigation routes in the app
sealed class Route(val route: String) { // Constructor accepts a route string

    object OnBoardingScreen : Route(route = "onBoardingScreen") // Represents the onboarding screen route

    // object HomeScreen: Route(route = "homeScreen") // Represents the home screen route (commented out)

    object SearchScreen : Route(route = "searchScreen") // Represents the search screen route

    object BookmarkScreen : Route(route = "bookmarkScreen") // Represents the bookmark screen route

    object YelpNavigation : Route(route = "yelpNavigation") // Represents the Yelp navigation route

    object YelpNavigatorScreen : Route(route = "yelpNavigatorScreen") // Represents the Yelp navigator screen route

    object AppStartNavigation : Route(route = "appStartNavigation") // Represents the app start navigation route
}