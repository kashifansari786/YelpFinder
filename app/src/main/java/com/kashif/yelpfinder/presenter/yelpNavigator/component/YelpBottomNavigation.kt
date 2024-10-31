package com.kashif.yelpfinder.presenter.yelpNavigator.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.kashif.yelpfinder.util.Dimens
import com.kashif.yelpfinder.ui.theme.Black

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
@Composable // Marks this function as a composable function
fun YelpBottomNavigation(
    items: List<BottomNavigationItems>, // List of bottom navigation items
    selected: Int, // Index of the currently selected item
    onItemClick: (Int) -> Unit // Callback function to handle item clicks
) {
    NavigationBar( // Create a navigation bar
        modifier = Modifier.fillMaxWidth(), // Make it fill the maximum width
        containerColor = MaterialTheme.colorScheme.background, // Set the background color from the theme
        tonalElevation = Dimens.Elevation // Set the elevation for shadow effect
    ) {
        items.forEachIndexed { index, item -> // Iterate over the items with index
            NavigationBarItem(
                selected = index == selected, // Determine if the current item is selected
                onClick = { onItemClick(index) }, // Handle item click and pass the index
                icon = { // Define the icon for the item
                    Column(horizontalAlignment = Alignment.CenterHorizontally) { // Center align items vertically
                        Icon(
                            painter = painterResource(id = item.icon), // Load the icon resource
                            contentDescription = null, // No content description for the icon
                            modifier = Modifier.size(Dimens.IconSize) // Set the size of the icon
                        )
                        Spacer(modifier = Modifier.height(Dimens.ExtraSmallPadding2)) // Add space between icon and text
                        Text(
                            text = item.text, // Set the text for the item
                            style = MaterialTheme.typography.labelSmall // Set the text style from the theme
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors( // Customize the colors for the navigation item
                    selectedIconColor = MaterialTheme.colorScheme.primary, // Color for selected icon
                    selectedTextColor = MaterialTheme.colorScheme.primary, // Color for selected text
                    unselectedIconColor = Black, // Color for unselected icon
                    indicatorColor = MaterialTheme.colorScheme.background // Color for the indicator background
                )
            )
        }
    }
}

// Data class to represent each item in the bottom navigation
data class BottomNavigationItems(
    @DrawableRes val icon: Int, // Resource ID for the icon drawable
    val text: String // Text label for the navigation item
)