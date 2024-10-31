package com.kashif.yelpfinder.presenter.onBoarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.kashif.yelpfinder.R
import com.kashif.yelpfinder.presenter.onBoarding.Pages
import com.kashif.yelpfinder.util.Dimens
import com.kashif.yelpfinder.ui.theme.Black
import com.kashif.yelpfinder.ui.theme.Body
import com.kashif.yelpfinder.ui.theme.TextMediumGray

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
@Composable
fun OnBoarding(modifier: Modifier=Modifier,pages: Pages){
    Column(modifier=modifier) {
        Image(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.6f),
            painter = painterResource(id=pages.image),
            contentDescription = null,
            contentScale = ContentScale.Crop)
        Spacer(modifier = Modifier.height(Dimens.MediumPadding24))
        Text(modifier = Modifier.padding(horizontal = Dimens.MediumPadding30),
            text = pages.title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = Black
        )
        Text(modifier = Modifier.padding(horizontal = Dimens.MediumPadding30),
            text = pages.description,
            style = MaterialTheme.typography.titleMedium,
            color = Body
        )
    }
}