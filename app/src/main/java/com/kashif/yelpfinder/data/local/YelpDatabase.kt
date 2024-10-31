package com.kashif.yelpfinder.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.kashif.yelpfinder.domain.model.Businesse

/**
 * Created by Mohammad Kashif Ansari on 31,October,2024
 */
// Annotates the class as a Room database with specified entities and version
@Database(entities = [Businesse::class], version = 3)

// Specifies the type converters to handle custom data types in Room
@TypeConverters(YelpTypeConverter::class)
abstract class YelpDatabase : RoomDatabase() {

    // Defines an abstract property for the DAO to access database operations
    abstract val yelpDao: YelpDao
}