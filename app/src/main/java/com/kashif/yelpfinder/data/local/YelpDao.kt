package com.kashif.yelpfinder.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kashif.yelpfinder.domain.model.Businesse
import kotlinx.coroutines.flow.Flow

/**
 * Created by Mohammad Kashif Ansari on 31,October,2024
 */
@Dao
interface YelpDao {

    // Inserts or replaces a Businesse entity in the database.
    // onConflict = OnConflictStrategy.REPLACE will overwrite the entry if it exists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(article: Businesse)

    // Deletes a specified Businesse entity from the database
    @Delete
    fun delete(article: Businesse)

    // Retrieves a flow of a list of all Businesse entities
    // Flow will allow for real-time updates when the database changes
    @Query("SELECT * FROM Businesse")
    fun getArticles(): Flow<List<Businesse>>

    // Retrieves a specific Businesse entity by its id
    // Using suspend here would make it suitable for background operations
    @Query("SELECT * FROM Businesse WHERE id=:id")
    suspend fun getArticle(id: String): Businesse
}