package com.kashif.yelpfinder.presenter.onBoarding

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kashif.yelpfinder.domain.useCases.app_entry.AppEntryUseCases
import com.kashif.yelpfinder.presenter.navGraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
@HiltViewModel // Annotation to specify that this ViewModel is provided by Hilt
class OnBoardingViewModel @Inject constructor(private val appEntryUseCases: AppEntryUseCases) : ViewModel() { // ViewModel class with injected dependencies

    // Function to handle events from the UI
    fun onEvent(event: OnboardingEvent) {
        when (event) { // Match the event type
            is OnboardingEvent.SaveAppEntry -> { // If event is SaveAppEntry
                saveAppEntry() // Call function to save app entry
            }
        }
    }

    // Function to save app entry
    private fun saveAppEntry() {
        viewModelScope.launch { // Launch a coroutine in the ViewModel's scope
            appEntryUseCases.saveAppEntry() // Call the use case to save app entry
        }
    }
}