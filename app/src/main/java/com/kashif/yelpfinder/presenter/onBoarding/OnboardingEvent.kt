package com.kashif.yelpfinder.presenter.onBoarding

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
// Sealed class to represent different events during the onboarding process
sealed class OnboardingEvent { // Base class for onboarding events

    object SaveAppEntry : OnboardingEvent() // Event to save app entry during onboarding
}