package com.example.nutriscan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutriscan.repository.FoodRepository
import com.example.nutriscan.room.FoodEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FoodViewModel(private val repo: FoodRepository) : ViewModel() {

    // Riwayat makanan
    private val _riwayat = MutableStateFlow<List<FoodEntity>>(emptyList())
    val riwayat: StateFlow<List<FoodEntity>> = _riwayat

    // Total kalori hari ini
    private val _kaloriHariIni = MutableStateFlow(0)
    val kaloriHariIni: StateFlow<Int> = _kaloriHariIni

    // Tambah makanan – status sukses/gagal
    private val _tambahStatus = MutableStateFlow<Boolean?>(null)
    val tambahStatus: StateFlow<Boolean?> = _tambahStatus.asStateFlow()

    private val _kaloriMingguan = MutableStateFlow<List<Int>>(emptyList())
    val kaloriMingguan = _kaloriMingguan.asStateFlow()

    fun ambilRiwayat() {
        viewModelScope.launch {
            repo.ambilRiwayat().collect {
                _riwayat.value = it
            }
        }
    }

    fun ambilKaloriHariIni() {
        viewModelScope.launch {
            repo.ambilKaloriHariIni().collect {
                _kaloriHariIni.value = it ?: 0
            }
        }
    }

    fun tambahMakanan(nama: String, kalori: Int, porsi: String, timestamp: Long) {
        viewModelScope.launch {
            val food = FoodEntity(
                name = nama,
                calories = kalori,
                portion = porsi,
                timestamp = timestamp
            )

            repo.tambahMakanan(food)
            _tambahStatus.value = true
        }
    }

    fun tambahMakanan(nama: String, kalori: Int) {
        val porsiDefault = "1"
        val timestamp = System.currentTimeMillis()

        tambahMakanan(
            nama = nama,
            kalori = kalori,
            porsi = porsiDefault,
            timestamp = timestamp
        )
    }

    fun ambilKaloriMingguan() {
        viewModelScope.launch {
            repo.ambilKaloriMingguan().collect { list ->
                // list = List<WeeklyCaloriesResult>
                val kaloriList = list.map { it.total ?: 0 }   // ambil nilai total
                _kaloriMingguan.value = kaloriList
            }
        }
    }

    fun hapusMakanan(food: FoodEntity) {
        viewModelScope.launch {
            repo.hapusMakanan(food)
        }
    }
}