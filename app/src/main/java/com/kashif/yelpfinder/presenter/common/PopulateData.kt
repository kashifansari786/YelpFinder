package com.kashif.yelpfinder.presenter.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.kashif.yelpfinder.domain.model.Businesse
import com.kashif.yelpfinder.presenter.search.SearchEvent
import com.kashif.yelpfinder.presenter.search.SearchViewModel
import com.kashif.yelpfinder.util.Dimens

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */

@Composable
fun PopulateData(
    modifier: Modifier = Modifier,
    event: (SearchEvent) -> Unit,
    viewModel: SearchViewModel,
    article: LazyPagingItems<Businesse>,
    onClick: (Businesse) -> Unit
) {
    // State to hold loading status or errors
    val lazyListState = rememberLazyListState()

    // Observe the loading state and errors
    // This assumes you have a way to manage loading states
    val loadingState by remember { mutableStateOf(false) } // Adjust based on your loading logic
    LazyColumn(
        state = lazyListState,
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimens.MediumPadding24),
        contentPadding = PaddingValues(all = Dimens.ExtraSmallPadding2)
    ) {
        items(count = article.itemCount) { index ->
            // Safely access the article item and handle nulls
            article[index]?.let { articleItem ->
                ArticleCard(
                    article = articleItem,
                    event = event,
                    viewModel=viewModel,
                    onclick = { onClick(articleItem) }
                )
            }
        }
    }
}

