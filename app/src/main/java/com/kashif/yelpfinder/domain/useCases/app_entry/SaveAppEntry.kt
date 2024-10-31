package com.kashif.yelpfinder.domain.useCases.app_entry

import com.kashif.yelpfinder.domain.manager.LocalUserManager

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */
class SaveAppEntry (private val localUserManager: LocalUserManager){

    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }
}