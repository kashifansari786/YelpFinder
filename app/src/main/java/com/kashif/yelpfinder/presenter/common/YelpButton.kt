package com.kashif.yelpfinder.presenter.common

import android.widget.Button
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kashif.yelpfinder.ui.theme.WhiteGray

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
@Composable
fun YelpButton(
    text: String, // The text to be displayed on the button
    onClick: () -> Unit // Callback function to be invoked when the button is clicked
) {
    Button(
        onClick = onClick, // Assign the click listener
        colors = ButtonDefaults.buttonColors( // Set button colors
            containerColor = MaterialTheme.colorScheme.primary, // Background color of the button
            contentColor = Color.White // Text color of the button
        ),
        shape = RoundedCornerShape(size = 6.dp) // Set the shape of the button's corners
    ) {
        Text(
            text = text, // Display the button text
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold) // Set the text style
        )
    }
}

@Composable
fun YelpTextButton(
    text: String, // The text to be displayed on the text button
    onClick: () -> Unit // Callback function to be invoked when the text button is clicked
) {
    TextButton(
        onClick = onClick, // Assign the click listener
        colors = ButtonDefaults.textButtonColors( // Set text button colors
            contentColor = WhiteGray // Text color of the text button
        )
    ) {
        Text(
            text = text, // Display the text button text
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold) // Set the text style
        )
    }
}