package com.kashif.yelpfinder.domain.useCases

import com.kashif.yelpfinder.domain.model.Businesse
import com.kashif.yelpfinder.domain.repository.YelpRepository

/**
 * Created by Mohammad Kashif Ansari on 31,October,2024
 */
// Class responsible for updating an article in the repository
class UpdateArticle(private val yelpRepository: YelpRepository) {

    // Operator function to invoke the update operation for a given article
    suspend operator fun invoke(businesse: Businesse) {
        yelpRepository.updateArticle(businesse) // Calls updateArticle method on the repository
    }
}