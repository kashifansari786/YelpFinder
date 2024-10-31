package com.kashif.yelpfinder.domain.useCases

import com.kashif.yelpfinder.domain.model.Businesse
import com.kashif.yelpfinder.domain.repository.YelpRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Mohammad Kashif Ansari on 31,October,2024
 */
// Class responsible for selecting all articles from the repository
class SelectArticles(private val yelpRepository: YelpRepository) {

    // Operator function to invoke the retrieval of all articles
    operator fun invoke(): Flow<List<Businesse>> {
        return yelpRepository.selectArticles() // Calls selectArticles method on the repository
    }
}