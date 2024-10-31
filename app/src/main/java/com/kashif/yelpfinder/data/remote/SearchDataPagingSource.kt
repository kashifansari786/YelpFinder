package com.kashif.yelpfinder.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kashif.yelpfinder.domain.model.Businesse
import com.kashif.yelpfinder.domain.model.SearchModel
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Mohammad Kashif Ansari on 31,October,2024
 */
// PagingSource for handling paginated data retrieval from YelpAPI based on search query and location
class SearchDataPagingSource(
    private val yelpAPI: YelpAPI,
    private val searchQuery: String,
    private val locationState: StateFlow<Pair<Double?, Double?>?>
) : PagingSource<Int, Businesse>() {

    // Tracks total item count from API response
    private var totalItemCount = 0

    // Provides refresh key based on the anchor position
    override fun getRefreshKey(state: PagingState<Int, Businesse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    // Loads data for a specific page from YelpAPI, returning LoadResult
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Businesse> {
        val page = params.key ?: 1
        return try {
            // Logging the current location from locationState
            Log.d("inside_searchdata", "location: ${locationState.value?.first}, ${locationState.value?.second}")

            // API call to retrieve search data with fixed coordinates for NYC (latitude/longitude hardcoded)
            val response = yelpAPI.getSearchData(
                searchQuery = searchQuery,
                radius = 200,
                limit = 20,
                latitude = locationState.value?.first!!,//40.712776, // Replace with locationState.value?.first when ready
                longitude = locationState.value?.first!!,//-74.005974 // Replace with locationState.value?.second when ready
            )

            // Increment total item count from API response
            totalItemCount += response.total

            // Distinct list of businesses by ID
            val article = response.businesses.distinctBy { it.id }

            // Returns paginated data
            LoadResult.Page(
                data = article,
                nextKey = if (totalItemCount == response.total) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(throwable = e)
        }
    }
}