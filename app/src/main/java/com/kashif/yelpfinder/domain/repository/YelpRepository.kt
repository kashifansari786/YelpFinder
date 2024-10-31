package com.kashif.yelpfinder.domain.repository

import androidx.paging.PagingData
import com.kashif.yelpfinder.domain.model.Businesse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
// Interface defining methods for Yelp data operations
interface YelpRepository {

    // Searches for businesses with pagination support based on search query and location
    fun searchData(
        searchQuery: String,
        locationState: StateFlow<Pair<Double?, Double?>?>
    ): Flow<PagingData<Businesse>>

    // Updates a given article in the database
    suspend fun updateArticle(article: Businesse)

    // Deletes a given article from the database
    suspend fun deleteArticle(article: Businesse)

    // Retrieves a list of all articles as a Flow
    fun selectArticles(): Flow<List<Businesse>>

    // Retrieves a specific article by its URL, returns null if not found
    suspend fun selectArticle(url: String): Businesse?
}