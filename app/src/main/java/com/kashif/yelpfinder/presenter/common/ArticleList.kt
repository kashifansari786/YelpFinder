package com.kashif.yelpfinder.presenter.common

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kashif.yelpfinder.domain.model.Businesse
import com.kashif.yelpfinder.presenter.search.SearchEvent
import com.kashif.yelpfinder.presenter.search.SearchViewModel
import com.kashif.yelpfinder.util.Dimens

/**
 * Created by Mohammad Kashif Ansari on 31,October,2024
 */
@Composable
fun ArticleList(
    modifier: Modifier = Modifier, // Modifier for customization
    article: List<Businesse>, // List of articles to display
    event: ((SearchEvent) -> Unit)?, // Event handler for article actions
    viewModel: SearchViewModel,
    onClick: (Businesse) -> Unit, // Click action for individual articles
) {

    // Check if internet connectivity is lost, display empty screen if true
//    if (updateInternetConnectivity) {
//        EmptyScreen() // Display empty screen component
//    }

    // Check if article list is empty, display empty screen if true
    if (article.isEmpty()) {
        EmptyScreen() // Display empty screen component
    }

    // LazyColumn for displaying articles
    LazyColumn(
        modifier = modifier.fillMaxSize(), // Fill available size
        verticalArrangement = Arrangement.spacedBy(Dimens.ExtraSmallPadding2), // Space between items
        contentPadding = PaddingValues(all = Dimens.ExtraSmallPadding2) // Padding around the content
    ) {
        // Iterate through the article list
        items(count = article.size) { index ->
            val articleData = article[index] // Get the article data at the current index
            ArticleCard(article = articleData, event = event, viewModel = viewModel, onclick = { onClick(articleData) }) // Display ArticleCard
        }
    }
}