package com.example.nutriscan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutriscan.repository.FoodRepository
import com.example.nutriscan.room.WeeklyCaloriesResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChartViewModel(private val repo: FoodRepository) : ViewModel() {

    private val _kaloriMingguan = MutableStateFlow<List<Int>>(emptyList())
    val kaloriMingguan: StateFlow<List<Int>> = _kaloriMingguan

    fun ambilKaloriMingguan() {
        viewModelScope.launch {
            repo.ambilKaloriMingguan().collect { list ->
                _kaloriMingguan.value =
                    list.map { it.total ?: 0 }  // ambil total dari WeeklyCaloriesResult
            }
        }
    }
}