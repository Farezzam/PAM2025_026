package com.example.nutriscan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutriscan.repository.FoodRepository
import com.example.nutriscan.room.WeeklyCaloriesResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChartViewModel(private val repo: FoodRepository) : ViewModel() {

    private val _kaloriMingguan = MutableStateFlow<List<WeeklyCaloriesResult>>(emptyList())
    val kaloriMingguan: StateFlow<List<WeeklyCaloriesResult>> = _kaloriMingguan

    fun ambilKaloriMingguan() {
        viewModelScope.launch {
            repo.ambilKaloriMingguan().collect {
                _kaloriMingguan.value = it
            }
        }
    }
}