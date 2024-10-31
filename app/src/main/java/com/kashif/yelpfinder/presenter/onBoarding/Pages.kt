package com.kashif.yelpfinder.presenter.onBoarding

import androidx.annotation.DrawableRes
import com.kashif.yelpfinder.R
/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
data class Pages(val title:String, val description: String,@DrawableRes val image: Int)

val pages= listOf(
    Pages(title = "Search Pizza", description = "Find your favorite pizza spots near your office or home across the USA. \uD83C\uDF55", image = R.drawable.pizza),
    Pages(title = "Search Juices", description = "Find your favorite pizza spots near your office or home across the USA.  \uD83E\uDD64", image = R.drawable.juices),
    Pages(title = "Search Anything", description = "Search for anything on this app with Yelp Fusion API integration.", image = R.drawable.search_restaurant)
)
