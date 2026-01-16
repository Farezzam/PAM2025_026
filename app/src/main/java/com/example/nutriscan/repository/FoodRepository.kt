package com.example.nutriscan.repository

import com.example.nutriscan.room.FoodDao
import com.example.nutriscan.room.FoodEntity
import kotlinx.coroutines.flow.Flow

class FoodRepository(
    private val dao: FoodDao
) {

    // Insert makanan
    suspend fun tambahMakanan(food: FoodEntity) {
        dao.insertFood(food)
    }

    // Hapus makanan
    suspend fun hapusMakanan(food: FoodEntity) {
        dao.deleteFood(food)
    }

    // Ambil semua riwayat makanan
    fun ambilRiwayat(): Flow<List<FoodEntity>> {
        return dao.getAllFoods()
    }

    // Total kalori hari ini untuk dashboard
    fun ambilKaloriHariIni(): Flow<Int?> {
        return dao.getTodayCalories()
    }

    // Data 7 hari terakhir untuk grafik
    fun ambilKaloriMingguan() = dao.getWeeklyCalories()
}