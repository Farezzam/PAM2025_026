package com.example.nutriscan.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: FoodEntity)

    @Delete
    suspend fun deleteFood(food: FoodEntity)

    @Query("SELECT * FROM foods ORDER BY timestamp DESC")
    fun getAllFoods(): Flow<List<FoodEntity>>

    @Query("""
        SELECT SUM(calories) FROM foods 
        WHERE date(timestamp / 1000, 'unixepoch') = date('now')
    """)
    fun getTodayCalories(): Flow<Int?>

    @Query("""
        SELECT date(timestamp / 1000, 'unixepoch') AS day, SUM(calories) AS total
        FROM foods
        WHERE day BETWEEN date('now', '-6 days') AND date('now')
        GROUP BY day
        ORDER BY day ASC
    """)
    fun getWeeklyCalories(): Flow<List<WeeklyCaloriesResult>>

    @Update
    suspend fun updateFood(food: FoodEntity)

    @Query("SELECT * FROM foods WHERE id = :id")
    suspend fun getFoodById(id: Int): FoodEntity?
}

data class WeeklyCaloriesResult(
    val day: String,
    val total: Int?
)