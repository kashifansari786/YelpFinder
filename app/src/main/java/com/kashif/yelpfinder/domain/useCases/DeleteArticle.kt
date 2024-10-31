package com.kashif.yelpfinder.domain.useCases

import com.kashif.yelpfinder.domain.model.Businesse
import com.kashif.yelpfinder.domain.repository.YelpRepository

/**
 * Created by Mohammad Kashif Ansari on 31,October,2024
 */
// Class responsible for deleting an article from the repository
class DeleteArticle(private val yelpRepository: YelpRepository) {

    // Operator function to invoke the delete operation
    suspend operator fun invoke(businesse: Businesse) {
        yelpRepository.deleteArticle(businesse) // Calls deleteArticle method on the repository
    }
}