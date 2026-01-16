package com.example.nutriscan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutriscan.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repo: AuthRepository) : ViewModel() {

    private val _loginStatus = MutableStateFlow(false)
    val loginStatus: StateFlow<Boolean> = _loginStatus

    private val _loginResult = MutableStateFlow<Boolean?>(null)
    val loginResult: StateFlow<Boolean?> = _loginResult

    fun cekStatusLogin() {
        viewModelScope.launch {
            repo.ambilStatusLogin().collect {
                _loginStatus.value = it
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val hasil = repo.login(email, password)
            _loginResult.value = hasil

            if (hasil) repo.simpanStatusLogin(true)
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            repo.daftarPengguna(email, password)
        }
    }

    fun logout() {
        viewModelScope.launch {
            repo.logout()
        }
    }
}