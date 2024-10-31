package com.kashif.yelpfinder.presenter.common

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kashif.yelpfinder.R
import com.kashif.yelpfinder.domain.model.Businesse
import com.kashif.yelpfinder.presenter.search.SearchEvent
import com.kashif.yelpfinder.presenter.search.SearchViewModel
import com.kashif.yelpfinder.ui.theme.Black
import com.kashif.yelpfinder.util.Dimens
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Created by Mohammad Kashif Ansari on 31,October,2024
 */

// Composable function to display an article card with relevant information
@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    event: ((SearchEvent) -> Unit)?,
    viewModel: SearchViewModel,
    article: Businesse,
    onclick: () -> Unit,
) {
    val context = LocalContext.current // Get current context
    val isOpenNow = article.business_hours.firstOrNull()?.is_open_now ?: false // Check if the business is currently open
    val openCloseText = if (isOpenNow) "Open" else "Closed" // Set text based on open status
    val openCloseColor = if (isOpenNow) Color.Green else Color.Red // Set color based on open status

    // Extract today's opening and closing times and convert to readable format
    val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1 // Get today's day index (0-based)
    val todayHours = article.business_hours.firstOrNull()?.open?.find { it.day == today } // Get today's hours
    val timingText = todayHours?.let {
        "${convertToReadableTime(it.start)} - ${convertToReadableTime(it.end)}" // Format timing text
    } ?: "No timing available" // Default text if no hours are available

    val bookmark = remember { mutableStateOf(article.isBookmarked) } // State to manage bookmark status

    Row(
        modifier = modifier
            .clickable { onclick() } // Set click listener
            .padding(Dimens.ExtraSmallPadding2) // Padding for the row
    ) {
        // Image for the article
        AsyncImage(
            model = ImageRequest.Builder(context = context)
                .data(article.image_url) // Load image URL
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(Dimens.ArticleCardSize) // Set size for the image
                .clip(MaterialTheme.shapes.medium) // Clip image with defined shape
        )
        Column(
            modifier = Modifier
                .padding(horizontal = Dimens.ExtraSmallPadding) // Horizontal padding
                .weight(1f) // Take remaining space
        ) {
            // Article title
            Text(
                text = article.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold), // Bold title
                color = Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis // Handle overflow
            )

            Spacer(modifier = Modifier.height(Dimens.ExtraSmallPadding2)) // Space between elements

            // Rating and review count row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Display the rating as a bar
                RatingBar(rating = article.rating.toFloat())
                Spacer(modifier = Modifier.width(Dimens.ExtraSmallPadding)) // Space between rating and text
                // Display the rating text
                Text(
                    text = article.rating.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(Dimens.ExtraSmallPadding)) // Space between elements
                // Display the review count
                Text(
                    text = "(${article.review_count} reviews)",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(Dimens.ExtraSmallPadding2)) // Space between elements

            // Open/Close and Timing row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = openCloseText,
                    color = openCloseColor,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold) // Bold status text
                )

                Spacer(modifier = Modifier.width(Dimens.ExtraSmallPadding)) // Space between elements

                Text(
                    text = timingText,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }

        Log.d("inside_article","bookmark:- "+article.isBookmarked+", "+article.name)

        // Bookmark Icon
        IconButton(
            onClick = {

                // Check if event is not null
                event?.let {
                    Log.d("inside_articleca", "event:- not null")

                    // Toggle the bookmark state
                    val updatedArticle = article.copy(isBookmarked = !article.isBookmarked) // Toggle bookmark status

                    // Trigger event with the updated article
                    it(SearchEvent.UpdateDeleteArticle(updatedArticle))
                    bookmark.value=!article.isBookmarked
                    viewModel.toggleBookmark(article)
                    // Update the local state immediately to reflect the change in UI
                    // Assuming you have a way to update the article list, like a mutable state
                    // You can directly update the list in your ViewModel or a higher state
                    // For example:
                    // _savedState.value = _savedState.value.copy(article = updatedArticleList)
                }
            },
            modifier = Modifier
                .padding(Dimens.ExtraSmallPadding) // Padding for icon button
                .size(Dimens.IconSize) // Set size for the icon
        ) {
            Icon(
                painter = painterResource(
                    id = if (bookmark.value) R.drawable.baseline_bookmark_added_24 else R.drawable.baseline_bookmark_border_24 // Select icon based on bookmark status
                ),
                contentDescription = "Bookmark"
            )
        }
    }
}

// Composable function to display a rating bar for an article
@Composable
fun RatingBar(rating: Float) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        for (i in 1..5) { // Loop through the rating stars
            Icon(
                painter = painterResource(if (i <= rating) R.drawable.star_filled else R.drawable.star), // Set star icon based on rating
                contentDescription = null,
                modifier = Modifier.size(Dimens.ShimmerSmallBoxHeight) // Set size for stars
            )
        }
    }
}

// Helper function to convert 24-hour format (e.g., "1000") to 12-hour format with AM/PM
fun convertToReadableTime(time: String): String {
    if (time.length != 4) return time // Return as is if the format is invalid

    val hour = time.substring(0, 2).toInt() // Extract hour
    val minute = time.substring(2, 4).toInt() // Extract minute
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour) // Set hour in calendar
        set(Calendar.MINUTE, minute) // Set minute in calendar
    }
    val format = SimpleDateFormat("hh:mm a", Locale.getDefault()) // Define format for time
    return format.format(calendar.time) // Format and return time
}
