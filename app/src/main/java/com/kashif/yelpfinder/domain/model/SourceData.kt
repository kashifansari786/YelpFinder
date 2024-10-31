package com.kashif.yelpfinder.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Mohammad Kashif Ansari on 31,October,2024
 */
@Parcelize
data class SourceData(val id:String,val name:String): Parcelable
