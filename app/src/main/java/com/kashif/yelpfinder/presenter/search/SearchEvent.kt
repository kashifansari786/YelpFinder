package com.kashif.yelpfinder.presenter.search

import com.kashif.yelpfinder.domain.model.Businesse

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
// Sealed class to represent different events related to searching
sealed class SearchEvent {

    // Data class for updating the search query with the provided search string
    data class UpdateSearchQuery(val searchQuery: String) : SearchEvent()

    // Object to represent the event of searching data
    object SearchData : SearchEvent()

    // Data class for updating or deleting an article, encapsulating the article data
    data class UpdateDeleteArticle(val article: Businesse) : SearchEvent()

    // Object to represent the removal of side effects in the search process
    object RemoveSideEffect : SearchEvent()
}