package com.kashif.yelpfinder.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.kashif.yelpfinder.domain.manager.LocalUserManager
import com.kashif.yelpfinder.util.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Mohammad Kashif Ansari on 31,October,2024
 */
// Implementation of LocalUserManager interface to manage app preferences
class LocalUserManagerImpl(private val context: Context) : LocalUserManager {

    // Saves app entry status as true in the DataStore
    override suspend fun saveAppEntry() {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.APP_ENTRY] = true
        }
    }

    // Reads app entry status from the DataStore, returns false if not set
    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.APP_ENTRY] ?: false
        }
    }
}

// Extension property to create DataStore instance with the specified name
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constant.USER_SETTINGS)

// Object to hold DataStore preference keys
private object PreferenceKeys {
    val APP_ENTRY = booleanPreferencesKey(name = Constant.APP_ENTRY)
}

// Class to manage location data using DataStore
class PreferenceClass(private val context: Context) {

    companion object {
        // Preference keys for storing latitude and longitude
        private val LATITUDE_KEY = doublePreferencesKey(Constant.LATITUDE)
        private val LONGITUDE_KEY = doublePreferencesKey(Constant.LONGITUDE)
    }

    // Saves latitude and longitude values in the DataStore
    suspend fun saveLocation(latitude: Double, longitude: Double) {
        context.dataStore.edit { preferences ->
            preferences[LATITUDE_KEY] = latitude
            preferences[LONGITUDE_KEY] = longitude
        }
    }

    // Retrieves location data as a Flow of Pair containing latitude and longitude
    val locationData: Flow<Pair<Double?, Double?>> = context.dataStore.data
        .map { preferences ->
            val latitude = preferences[LATITUDE_KEY]
            val longitude = preferences[LONGITUDE_KEY]
            Pair(latitude, longitude)
        }
}
