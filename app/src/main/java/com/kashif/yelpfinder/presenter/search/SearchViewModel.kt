package com.kashif.yelpfinder.presenter.search

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.paging.map
import com.kashif.yelpfinder.data.manager.PreferenceClass
import com.kashif.yelpfinder.domain.model.Businesse
import com.kashif.yelpfinder.domain.useCases.YelpUseCases
import com.kashif.yelpfinder.presenter.bookmark.BookmarkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
@HiltViewModel // Annotation for Hilt dependency injection in ViewModel
class SearchViewModel @Inject constructor(
    private val yelpUseCases: YelpUseCases, // Use cases related to Yelp operations
    private val preferenceClass: PreferenceClass // Class for managing user preferences
) : ViewModel() {

    private val _state = mutableStateOf(SearchState()) // State of the search screen
    val state: State<SearchState> = _state // Expose the state as immutable



    private val _locationState = MutableStateFlow<Pair<Double?, Double?>?>(null) // StateFlow to hold location data
    val locationState: StateFlow<Pair<Double?, Double?>?> = _locationState // Expose location state

    var sideEffect by mutableStateOf<String?>(null) // State for side effects
        private set // Prevent external modification of sideEffect

    // Function to handle various search events
    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateSearchQuery -> { // Update search query event
                _state.value = _state.value.copy(searchQuery = event.searchQuery) // Update the state with the new query
            }
            is SearchEvent.SearchData -> { // Search data event
                fetchLocationData() // Fetch the current location data
                fetchSearchDataAndCheckArticles()
                //SearchData() // Perform the search operation
            }
            is SearchEvent.UpdateDeleteArticle -> { // Event for updating or deleting an article
                Log.d("inside_articleca", "inside update delete article") // Log statement for debugging
                viewModelScope.launch { // Launch coroutine in the ViewModel's scope
                    handleUpdateDeleteArticle(event.article) // Handle the update or delete operation for the article
                }
            }
            is SearchEvent.RemoveSideEffect -> { // Remove side effect event
                sideEffect = null // Clear the side effect
            }
        }
    }
    private fun fetchSearchDataAndCheckArticles() {
        viewModelScope.launch {

            // Collect the articles from the Room database
            val savedArticles = yelpUseCases.selectArticles().first() // Collect first value of saved articles

            // Create a set of saved article IDs for quick lookup
            val savedArticleIds = savedArticles.map { it.id }.toSet()

            // Fetch the search data from the server
            val searchDataFlow = yelpUseCases.getSearchData(
                searchQuery = _state.value.searchQuery,
                locationState // Pass the current location state
            ).cachedIn(viewModelScope) // Cache the data in ViewModel's scope

            // Observe the PagingData and map bookmark status
            val articlesWithBookmarkFlow = searchDataFlow.map { pagingData ->
                pagingData.map { article ->
                    // Check if the article is in the saved article IDs and update the bookmark status
                    article.copy(isBookmarked = savedArticleIds.contains(article.id))
                }
            }

            // Update the state with the Flow of articles having bookmark status
            _state.value = _state.value.copy(searchData = articlesWithBookmarkFlow)

        }
    }

    // Function to toggle bookmark
    fun toggleBookmark(article: Businesse) {
        val updatedBookmarksFlow = _state.value.searchData?.map { pagingData ->
            pagingData.map { bookmark ->
                if (bookmark.id == article.id) {
                    bookmark.copy(isBookmarked = !article.isBookmarked)
                } else {
                    bookmark
                }
            }
        }?.cachedIn(viewModelScope) // Ensure cached flow to prevent re-fetching
        // Update the state with the modified flow
        _state.value = _state.value.copy(searchData = updatedBookmarksFlow)
    }

    // Function to fetch location data
    fun fetchLocationData() {
        viewModelScope.launch { // Launch coroutine in the ViewModel's scope
            preferenceClass.locationData.collect { location -> // Collect location data from preferences
                _locationState.value = location // Update the location state
            }
        }
    }

    // Function to perform the search operation
    private fun SearchData() {
        val data = yelpUseCases.getSearchData(
            searchQuery = _state.value.searchQuery, // Get search query from state
            locationState // Pass the current location state
        ).cachedIn(viewModelScope) // Cache the data in ViewModel's scope
        _state.value = _state.value.copy(searchData = data) // Update the state with the fetched data
    }

    // Function to handle update or delete operations for an article
    private suspend fun handleUpdateDeleteArticle(article: Businesse) {
        withContext(Dispatchers.IO) { // Switch to IO context for database operations
            val existingArticle = yelpUseCases.selectArticle(article.id) // Check if the article already exists
            Log.d("inside_articleca", "article:- " + existingArticle?.id) // Log for debugging
            if (existingArticle == null) { // If the article does not exist
                updateArticle(article) // Update the article
            } else {
                deleteArticle(article) // Delete the existing article
            }
        }
    }

    // Function to delete an article
    private suspend fun deleteArticle(article: Businesse) {
        withContext(Dispatchers.IO) { // Switch to IO context
            yelpUseCases.deleteArticle(businesse = article) // Call use case to delete the article
            sideEffect = "Article Deleted" // Set side effect message
        }
    }

    // Function to update an article
    private suspend fun updateArticle(article: Businesse) {
        withContext(Dispatchers.IO) { // Switch to IO context
            yelpUseCases.updateArticle(businesse = article) // Call use case to update the article
            sideEffect = "Article Saved" // Set side effect message
        }
    }
}