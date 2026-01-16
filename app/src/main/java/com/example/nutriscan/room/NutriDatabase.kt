package com.example.nutriscan.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FoodEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NutriDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
}