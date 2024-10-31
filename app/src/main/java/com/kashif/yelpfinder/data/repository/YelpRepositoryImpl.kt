package com.kashif.yelpfinder.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kashif.yelpfinder.data.local.YelpDao
import com.kashif.yelpfinder.data.remote.SearchDataPagingSource
import com.kashif.yelpfinder.data.remote.YelpAPI
import com.kashif.yelpfinder.domain.model.Businesse
import com.kashif.yelpfinder.domain.repository.YelpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Mohammad Kashif Ansari on 31,October,2024
 */
// Implementation of YelpRepository for handling API calls and database operations
class YelpRepositoryImpl(
    private val yelpAPI: YelpAPI,
    private val yelpDao: YelpDao
) : YelpRepository {

    // Retrieves paginated search data based on search query and location
    override fun searchData(
        searchQuery: String,
        locationState: StateFlow<Pair<Double?, Double?>?>
    ): Flow<PagingData<Businesse>> {
        return Pager(
            config = PagingConfig(pageSize = 10), // Configures pagination with page size
            pagingSourceFactory = {
                SearchDataPagingSource(
                    searchQuery = searchQuery,
                    yelpAPI = yelpAPI,
                    locationState = locationState
                )
            }
        ).flow
    }

    // Updates an article in the local database
    override suspend fun updateArticle(article: Businesse) {
        yelpDao.update(article)
    }

    // Deletes an article from the local database
    override suspend fun deleteArticle(article: Businesse) {
        yelpDao.delete(article)
    }

    // Retrieves a list of all articles from the local database as a Flow
    override fun selectArticles(): Flow<List<Businesse>> {
        return yelpDao.getArticles()
    }

    // Retrieves a specific article by URL from the local database
    override suspend fun selectArticle(url: String): Businesse {
        return yelpDao.getArticle(url)
    }
}