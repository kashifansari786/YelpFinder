package com.kashif.yelpfinder.domain.useCases

import com.kashif.yelpfinder.domain.model.Businesse
import com.kashif.yelpfinder.domain.repository.YelpRepository

/**
 * Created by Mohammad Kashif Ansari on 31,October,2024
 */
// Class responsible for selecting a specific article from the repository
class SelectArticle(private val yelpRepository: YelpRepository) {

    // Operator function to invoke the selection of an article by its URL
    suspend operator fun invoke(url: String): Businesse? {
        return yelpRepository.selectArticle(url) // Calls selectArticle method on the repository
    }
}