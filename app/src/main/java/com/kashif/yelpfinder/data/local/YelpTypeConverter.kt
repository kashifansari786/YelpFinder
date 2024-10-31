package com.kashif.yelpfinder.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kashif.yelpfinder.domain.model.Attributes
import com.kashif.yelpfinder.domain.model.BusinessHour
import com.kashif.yelpfinder.domain.model.Category
import com.kashif.yelpfinder.domain.model.Coordinates
import com.kashif.yelpfinder.domain.model.Location
import com.kashif.yelpfinder.domain.model.SourceData
import okio.Source

/**
 * Created by Mohammad Kashif Ansari on 31,October,2024
 */
// Specifies that this class provides custom type conversion for Room
@ProvidedTypeConverter
class YelpTypeConverter {

    // Instance of Gson for converting objects to JSON strings and vice versa
    private val gson = Gson()

    // Converts an Attributes object to a JSON string for Room database storage
    @TypeConverter
    fun attributeToString(attributes: Attributes): String {
        return gson.toJson(attributes)
    }

    // Converts a JSON string back to an Attributes object when retrieving from Room
    @TypeConverter
    fun stringToAttributes(attributes: String): Attributes {
        return gson.fromJson(attributes, Attributes::class.java)
    }

    // Converts a Coordinates object to a JSON string for Room storage
    @TypeConverter
    fun coordinatesToString(attributes: Coordinates): String {
        return gson.toJson(attributes)
    }

    // Converts a JSON string back to a Coordinates object when retrieving from Room
    @TypeConverter
    fun stringToCoordinates(coordinates: String): Coordinates {
        return gson.fromJson(coordinates, Coordinates::class.java)
    }

    // Converts a Location object to a JSON string for Room storage
    @TypeConverter
    fun locationToString(attributes: Location): String {
        return gson.toJson(attributes)
    }

    // Converts a JSON string back to a Location object when retrieving from Room
    @TypeConverter
    fun stringToLocation(location: String): Location {
        return gson.fromJson(location, Location::class.java)
    }

    // Converts a list of transactions (strings) to a JSON string for Room storage
    @TypeConverter
    fun transactionListToString(transaction: List<String>): String {
        return gson.toJson(transaction)
    }

    // Converts a JSON string back to a list of transactions (strings) when retrieving from Room
    @TypeConverter
    fun stringToTransaction(transaction: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(transaction, type)
    }

    // Converts a list of BusinessHour objects to a JSON string for Room storage
    @TypeConverter
    fun businessHourListToString(businessHours: List<BusinessHour>): String {
        return gson.toJson(businessHours)
    }

    // Converts a JSON string back to a list of BusinessHour objects when retrieving from Room
    @TypeConverter
    fun stringToBusinessHourList(businessHourString: String): List<BusinessHour> {
        val type = object : TypeToken<List<BusinessHour>>() {}.type
        return gson.fromJson(businessHourString, type)
    }

    // Converts a list of Category objects to a JSON string for Room storage
    @TypeConverter
    fun categoryListToString(categories: List<Category>): String {
        return gson.toJson(categories)
    }

    // Converts a JSON string back to a list of Category objects when retrieving from Room
    @TypeConverter
    fun stringToCategoryList(categoryString: String): List<Category> {
        val type = object : TypeToken<List<Category>>() {}.type
        return gson.fromJson(categoryString, type)
    }
}