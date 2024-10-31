package com.kashif.yelpfinder.presenter.common

import android.annotation.SuppressLint
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import com.kashif.yelpfinder.ui.theme.Shimmer
import com.kashif.yelpfinder.util.Dimens

/**
 * Created by Mohammad Kashif Ansari on 31,October,2024
 */
@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.shimmerEffect() = composed {
    val transition = rememberInfiniteTransition()
    val alpha = transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    ).value
    background(color = Shimmer.copy(alpha = alpha))
}

@Composable
fun ArticleShimmerEffect(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        // Image Placeholder
        Box(
            modifier = Modifier
                .size(Dimens.ArticleCardSize)
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect() // Corrected spelling
        )

        // Text Placeholder
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(horizontal = Dimens.ExtraSmallPadding)
                .height(Dimens.ArticleCardSize)
        ) {
            // Title Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.ShimmerBoxHeight)
                    .shimmerEffect()
                    .padding(horizontal = Dimens.MediumPadding24)
            )

            // Rating Placeholder
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(Dimens.ShimmerSmallBoxHeight)
                        .shimmerEffect()
                        .padding(horizontal = Dimens.MediumPadding24)
                )
            }
        }
    }
}