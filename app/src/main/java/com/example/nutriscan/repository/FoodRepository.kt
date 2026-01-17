package com.example.nutriscan.repository

import com.example.nutriscan.room.FoodDao
import com.example.nutriscan.room.FoodEntity
import kotlinx.coroutines.flow.Flow

class FoodRepository(
    private val dao: FoodDao
) {

    suspend fun tambahMakanan(food: FoodEntity) {
        dao.insertFood(food)
    }

    suspend fun hapusMakanan(food: FoodEntity) {
        dao.deleteFood(food)
    }

    suspend fun updateMakanan(food: FoodEntity){
        dao.updateFood(food)
    }

    fun ambilRiwayat(): Flow<List<FoodEntity>> {
        return dao.getAllFoods()
    }

    fun ambilKaloriHariIni(): Flow<Int?> {
        return dao.getTodayCalories()
    }

    fun ambilKaloriMingguan() = dao.getWeeklyCalories()
}