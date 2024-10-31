package com.kashif.yelpfinder.presenter.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kashif.yelpfinder.domain.useCases.YelpUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Created by Mohammad Kashif Ansari on 31,October,2024
 */
// ViewModel for managing the state of bookmarked articles
@HiltViewModel
class BookmarkViewModel @Inject constructor(private val yelpUseCases: YelpUseCases) : ViewModel() {

    // Mutable state for BookmarkState
    private val _state = mutableStateOf(BookmarkState())

    // Exposed state for observing in the UI
    val state: State<BookmarkState> = _state

    // Initialize by fetching articles when ViewModel is created
    init {
        getArticles()
    }

    // Function to fetch and update articles in the state
    private fun getArticles() {
        yelpUseCases.selectArticles().onEach { articles ->
            _state.value = _state.value.copy(article = articles.asReversed()) // Update state with reversed article list
        }.launchIn(viewModelScope) // Launch coroutine in ViewModel's scope
    }
}