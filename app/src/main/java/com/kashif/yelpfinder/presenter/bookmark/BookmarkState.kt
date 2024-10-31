package com.kashif.yelpfinder.presenter.bookmark

import com.kashif.yelpfinder.domain.model.Businesse

/**
 * Created by Mohammad Kashif Ansari on 31,October,2024
 */
// Data class representing the state of bookmarked articles
data class BookmarkState(
    val article: List<Businesse> = emptyList() // List of bookmarked articles, initialized to an empty list
)
