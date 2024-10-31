package com.kashif.yelpfinder.presenter.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.kashif.yelpfinder.domain.model.Businesse
import com.kashif.yelpfinder.presenter.common.ArticleShimmerEffect
import com.kashif.yelpfinder.presenter.common.CustomSearchBar
import com.kashif.yelpfinder.presenter.common.EmptyScreen
import com.kashif.yelpfinder.presenter.common.PopulateData
import com.kashif.yelpfinder.util.Dimens

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
@Composable
fun SearchScreen(state:SearchState, // Current state of the search screen
                 event:(SearchEvent)->Unit, // Function to handle search events
                 viewModel: SearchViewModel,
                 navigationToDetails:(Businesse)->Unit, // Function for navigation to article details
                 updateInternetConnectivity:Boolean) // Boolean to check internet connectivity status
{
    Column(modifier = Modifier.fillMaxSize() // Fill the entire available space
        .padding(
        top = Dimens.ExtraSmallPadding2,
        start = Dimens.ExtraSmallPadding2,
        end = Dimens.ExtraSmallPadding2
    ).statusBarsPadding()) // Adjust padding for status bars
    {
        // Custom search bar for entering search queries
        CustomSearchBar(text= state.searchQuery,
            readOnly = false,
            onValueChange = {event(SearchEvent.UpdateSearchQuery(it))},
            onSearch = {event(SearchEvent.SearchData)})
        Spacer(modifier = Modifier.height(Dimens.MediumPadding24))
        state.searchData?.let {
            val data=it.collectAsLazyPagingItems()
            if(updateInternetConnectivity){
                val handlePagingResult= handlePagingResult(article=data)
                if(handlePagingResult){
                    PopulateData(
                        article = data,
                        event=event,
                        viewModel=viewModel,
                        onClick ={navigationToDetails(it)})
                }
            }


        }
    }
}
// Function to handle the paging result and display appropriate UI
@Composable
fun handlePagingResult(article: LazyPagingItems<Businesse>):Boolean{
    val loadState=article.loadState
    val error=when{
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }
    return  when{
        loadState.refresh is LoadState.Loading ->{ // If loading
            ShimmerEffect() // Show shimmer effect
            false // Return false to indicate loading state
        }
        error !=null ->{ // If there is an error
            EmptyScreen(error=error) // Show empty screen with error
            false // Return false for error state
        }
        article.itemCount==0 ->{ // If no items found
            EmptyScreen() // Show empty screen
            false // Return false for no data state
        }
        else ->{ // If items are available
            true // Return true to indicate data is ready to display
        }
    }
}
@Composable
private fun ShimmerEffect(){
    Column(verticalArrangement = Arrangement.spacedBy(Dimens.ExtraSmallPadding2)) {
        repeat(10){
            ArticleShimmerEffect(modifier=Modifier.padding(horizontal = Dimens.ExtraSmallPadding2))
        }
    }
}