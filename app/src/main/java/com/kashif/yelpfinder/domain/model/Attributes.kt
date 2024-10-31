package com.kashif.yelpfinder.domain.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


data class Attributes(
    val business_temp_closed: Any,
    val menu_url: String,
    val open24_hours: Any,
    val waitlist_reservation: Any
)