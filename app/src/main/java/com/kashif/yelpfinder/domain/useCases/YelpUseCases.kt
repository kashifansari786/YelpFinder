package com.kashif.yelpfinder.domain.useCases

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
// Data class that encapsulates various use cases related to Yelp operations
data class YelpUseCases(
    val getSearchData: GetSearchData,     // Use case for retrieving search data
    val updateArticle: UpdateArticle,      // Use case for updating an article
    val deleteArticle: DeleteArticle,      // Use case for deleting an article
    val selectArticles: SelectArticles,     // Use case for selecting all articles
    val selectArticle: SelectArticle        // Use case for selecting a specific article
)
