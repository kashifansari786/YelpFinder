package com.kashif.yelpfinder.domain.useCases.app_entry

import com.kashif.yelpfinder.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
class ReadAppEntry(private val localUserManager: LocalUserManager) {

    operator fun invoke(): Flow<Boolean> {
        return localUserManager.readAppEntry()
    }
}