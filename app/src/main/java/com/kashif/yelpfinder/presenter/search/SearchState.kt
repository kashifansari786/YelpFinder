package com.kashif.yelpfinder.presenter.search

import androidx.paging.PagingData
import com.kashif.yelpfinder.domain.model.Businesse
import com.kashif.yelpfinder.domain.model.SearchModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
data class SearchState(val searchQuery:String="",val searchData:Flow<PagingData<Businesse>>?=null)
