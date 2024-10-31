//package com.kashif.yelpfinder.util
//
//import android.content.Context
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.core.booleanPreferencesKey
//import androidx.datastore.preferences.core.doublePreferencesKey
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.preferencesDataStore
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//
///**
// * Created by Mohammad Kashif Ansari on 31,October,2024
// */
//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name=Constant.USER_SETTINGS)
//class PreferenceClass(private val context: Context) {
//    // Preference Keys for Latitude and Longitude
//   companion object{
//       private val LATITUDE_KEY= doublePreferencesKey(Constant.LATITUDE)
//       private val LONGITUDE_KEY= doublePreferencesKey(Constant.LONGITUDE)
//   }
//
//    // Function to Save Location Data
//    suspend fun saveLocation(latitude: Double, longitude: Double) {
//        context.dataStore.edit { preferences ->
//            preferences[LATITUDE_KEY] = latitude
//            preferences[LONGITUDE_KEY] = longitude
//        }
//    }
//
//    // Function to Retrieve Location Data as Flow
//    val locationData: Flow<Pair<Double?, Double?>> = context.dataStore.data
//        .map { preferences ->
//            val latitude = preferences[LATITUDE_KEY]
//            val longitude = preferences[LONGITUDE_KEY]
//            Pair(latitude, longitude) // Return latitude and longitude as Pair
//        }
//}