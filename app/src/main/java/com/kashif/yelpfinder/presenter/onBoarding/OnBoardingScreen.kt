package com.kashif.yelpfinder.presenter.onBoarding

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kashif.yelpfinder.presenter.common.YelpButton
import com.kashif.yelpfinder.presenter.common.YelpTextButton
import com.kashif.yelpfinder.presenter.navGraph.Route
import com.kashif.yelpfinder.presenter.onBoarding.component.OnBoarding
import com.kashif.yelpfinder.presenter.onBoarding.component.PageIndicator
import com.kashif.yelpfinder.util.Dimens
import kotlinx.coroutines.launch

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */

@OptIn(ExperimentalFoundationApi::class) // Opt-in to use experimental APIs
@Composable
fun OnBoardingScreen(event: (OnboardingEvent) -> Unit) { // Composable function for onboarding screen
    Column(modifier = Modifier.fillMaxSize()) { // Column layout that fills the entire size
        // Horizontal pager state using rememberState
        val pagerState = rememberPagerState(initialPage = 0) { // Remembering the pager state
            // Number of pages
            pages.size // Total number of pages in the onboarding
        }

        // Derived state to determine button labels based on the current page
        val buttonState = remember {
            derivedStateOf { // Create a derived state
                when (pagerState.currentPage) { // Switch based on current page
                    0 -> listOf("", "Next") // First page buttons
                    1 -> listOf("Back", "Next") // Second page buttons
                    2 -> listOf("Back", "Get Started") // Last page buttons
                    else -> listOf("", "") // Default case
                }
            }
        }

        // Horizontal pager that allows swiping between onboarding pages
        HorizontalPager(state = pagerState) { index -> // Pager that takes the pager state
            OnBoarding(pages = pages[index]) // Display current onboarding page
        }

        Spacer(modifier = Modifier.weight(1f)) // Spacer to take up remaining space

        // Row for buttons and page indicator
        Row(
            modifier = Modifier
                .fillMaxWidth() // Fill the width of the screen
                .padding(horizontal = Dimens.MediumPadding24) // Padding on horizontal sides
                .navigationBarsPadding(), // Adjusts for navigation bar height
            horizontalArrangement = Arrangement.SpaceBetween, // Space buttons evenly
            verticalAlignment = Alignment.CenterVertically // Align vertically centered
        ) {
            // Page indicator for displaying current page
            PageIndicator(
                pageSize = pages.size, // Total number of pages
                selectedPage = pagerState.currentPage, // Current selected page
                modifier = Modifier.width(Dimens.PageIndicatorWidth) // Width for the indicator
            )

            Row(verticalAlignment = Alignment.CenterVertically) { // Row for buttons
                // Coroutine scope for launching animations
                val scope = rememberCoroutineScope() // Remembering coroutine scope

                // Check if the first button's text is not empty
                if (buttonState.value[0].isNotEmpty()) { // If back button should be shown
                    // Text button for navigating back
                    YelpTextButton(text = buttonState.value[0], onClick = {
                        scope.launch { // Launch coroutine
                            // Animate to the previous page
                            pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                        }
                    })
                }

                // Main button for navigation forward
                YelpButton(text = buttonState.value[1], onClick = {
                    scope.launch { // Launch coroutine
                        Log.d("inside_mainacti", "pagerstate:- " + pagerState.currentPage) // Debug log

                        // Check if on the last onboarding page
                        if (pagerState.currentPage == 2) {
                            // Navigate to home screen
                            event(OnboardingEvent.SaveAppEntry) // Trigger save app entry event
                        } else {
                            // Animate to the next page
                            pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                        }
                    }
                })
            }
        }

        Spacer(modifier = Modifier.weight(0.5f)) // Additional spacer to balance layout
    }
}