package com.kashif.yelpfinder.presenter.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.kashif.yelpfinder.domain.model.Businesse
import com.kashif.yelpfinder.presenter.common.ArticleList
import com.kashif.yelpfinder.presenter.search.SearchEvent
import com.kashif.yelpfinder.presenter.search.SearchViewModel
import com.kashif.yelpfinder.ui.theme.Black
import com.kashif.yelpfinder.util.Dimens.ExtraSmallPadding2
import com.kashif.yelpfinder.util.Dimens.MediumPadding24

/**
 * Created by Mohammad Kashif Ansari on 31,October,2024
 */
@Composable
fun BookmarkScreen(
    state: BookmarkState, // State containing the list of bookmarked articles
    viewModel: SearchViewModel,
    event: (SearchEvent)->Unit,
    navigationToDetails: (Businesse) -> Unit, // Lambda to handle navigation to article details

) {
    Column(
        modifier = Modifier
            .fillMaxSize() // Fill the available size
            .statusBarsPadding() // Apply padding to avoid the status bar
            .padding(top = ExtraSmallPadding2, start = ExtraSmallPadding2, end = ExtraSmallPadding2) // Apply custom padding
    ) {
        // Title for the Bookmark Screen
        Text(
            text = "Bookmark",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold), // Use Material Theme for styling
            color = Black // Set text color to black
        )

        Spacer(modifier = Modifier.height(MediumPadding24)) // Add space between title and article list

        // Display a list of articles
        ArticleList(
            article = state.article, // Pass the articles from state
            onClick = { navigationToDetails(it) }, // Pass the click handler for article details
            event = event, // No event handling specified
            viewModel=viewModel
        )
    }
}