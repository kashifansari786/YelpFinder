package com.kashif.yelpfinder.domain.model

data class SearchModel(
    val businesses: List<Businesse>,
    val region: Region,
    val total: Int
)