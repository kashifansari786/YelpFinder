package com.kashif.yelpfinder.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@Entity
data class Businesse(
    val alias: String,
    val attributes: @RawValue Attributes,
    val business_hours:@RawValue  List<BusinessHour>,
    val categories: @RawValue List<Category>,
    val coordinates: @RawValue Coordinates,
    val display_phone: String,
    val distance: Double,

    @PrimaryKey
    val id: String,
    val image_url: String,
    val is_closed: Boolean,
    val location: @RawValue Location,
    val name: String,
    val phone: String,
    val price: String?,
    val rating: Double,
    val review_count: Int,
    val transactions: @RawValue List<String>,
    val url: String,
    // Add other existing properties here...
    var isBookmarked: Boolean = false // New property to indicate if the article is bookmarked
):Parcelable {

}