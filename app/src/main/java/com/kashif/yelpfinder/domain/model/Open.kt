package com.kashif.yelpfinder.domain.model

data class Open(
    val day: Int,
    val end: String,
    val is_overnight: Boolean,
    val start: String
)