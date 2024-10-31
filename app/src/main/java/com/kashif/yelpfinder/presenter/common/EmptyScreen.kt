package com.kashif.yelpfinder.presenter.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.paging.LoadState
import com.kashif.yelpfinder.R
import com.kashif.yelpfinder.ui.theme.LightBlack
import com.kashif.yelpfinder.util.Dimens.AnimIconSize
import com.kashif.yelpfinder.util.Dimens.Elevation
import java.lang.Error
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by Mohammad Kashif Ansari on 31,October,2024
 */
@Composable
fun EmptyScreen(error: LoadState.Error? = null) {
    // State variables for message and icon
    var message by remember { mutableStateOf(parseErrorMessage(error = error)) }
    var icon by remember { mutableStateOf(R.drawable.ic_network_error) }

    // Update message and icon based on the error state
    if (error == null) {
        message = "You have not saved any feed so far!"
        icon = R.drawable.ic_search_document
    }

    // Animation state
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 0.3f else 0f,
        animationSpec = tween(durationMillis = 1500),
        label = ""
    )

    // Start animation when the Composable is first launched
    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    // Render the empty content
    EmptyContent(alphaAnim = alphaAnimation, message = message, iconId = icon)
}

@Composable
fun EmptyContent(alphaAnim: Float, message: String, iconId: Int) {
    // Layout for empty content
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Display the icon with animation
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
            modifier = Modifier
                .size(AnimIconSize)
                .alpha(alphaAnim) // Apply the animated alpha
        )

        // Display the message with animation
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
            modifier = Modifier
                .padding(Elevation)
                .alpha(alphaAnim) // Apply the animated alpha
        )
    }
}

// Function to parse error messages based on the type of error
fun parseErrorMessage(error: LoadState.Error?): String {
    return when (error?.error) {
        is SocketTimeoutException -> {
            "Server Unavailable"
        }
        is ConnectException -> {
            "Internet Unavailable"
        }
        else -> {
            "Unknown error"
        }
    }
}