package com.kashif.yelpfinder.domain.useCases

import androidx.paging.PagingData
import com.kashif.yelpfinder.domain.model.Businesse
import com.kashif.yelpfinder.domain.repository.YelpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
// Class responsible for retrieving search data from the repository
class GetSearchData(private val yelpRepository: YelpRepository) {

    // Operator function to invoke the search data retrieval
    operator fun invoke(
        searchQuery: String,
        locationState: StateFlow<Pair<Double?, Double?>?>
    ): Flow<PagingData<Businesse>> {
        return yelpRepository.searchData(searchQuery, locationState) // Calls searchData method on the repository
    }
}