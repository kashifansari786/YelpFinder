package com.kashif.yelpfinder.domain.manager

import kotlinx.coroutines.flow.Flow

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */

//interface is created for starting app on boarding screen state
// Interface defining methods for managing local user settings
interface LocalUserManager {

    // Saves an entry status to persistent storage
    suspend fun saveAppEntry()

    // Reads the entry status from persistent storage as a Flow of Boolean
    fun readAppEntry(): Flow<Boolean>
}