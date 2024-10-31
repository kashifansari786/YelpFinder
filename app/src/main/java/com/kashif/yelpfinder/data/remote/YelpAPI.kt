package com.kashif.yelpfinder.data.remote

import com.kashif.yelpfinder.domain.model.SearchModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
* Created by Mohammad Kashif Ansari on 30,October,2024
*/
// Interface for Yelp API with endpoint to search for businesses based on location and query parameters
interface YelpAPI {

    // Suspended function to fetch search data from Yelp API
    @GET("businesses/search")
    suspend fun getSearchData(
        @Query("term") searchQuery: String,      // Query term to search for businesses
        @Query("radius") radius: Int,            // Radius for search area in meters
        @Query("limit") limit: Int,              // Max number of results to return per page
        @Query("latitude") latitude: Double,     // Latitude of the location for search
        @Query("longitude") longitude: Double    // Longitude of the location for search
    ): SearchModel                             // Returns a SearchModel containing search results
}