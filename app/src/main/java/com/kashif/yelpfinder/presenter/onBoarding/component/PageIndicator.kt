package com.kashif.yelpfinder.presenter.onBoarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.kashif.yelpfinder.util.Dimens

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
@Composable
fun PageIndicator(modifier: Modifier=Modifier,
                  pageSize:Int,
                  selectedPage:Int,
                  selectedColor: Color=MaterialTheme.colorScheme.primary,
                  unSelectedColor:Color=Color.Blue){
    Row(modifier=modifier,
        horizontalArrangement = Arrangement.SpaceBetween) {
        repeat(pageSize){page->
            Box(modifier = Modifier
                .size(Dimens.IndicatorSize)
                .clip(CircleShape)
                .background(color = if(page==selectedPage) selectedColor else unSelectedColor))
        }
    }
}