package com.kashif.yelpfinder.domain.model

data class BusinessHour(
    val hours_type: String,
    val is_open_now: Boolean,
    val `open`: List<Open>
)