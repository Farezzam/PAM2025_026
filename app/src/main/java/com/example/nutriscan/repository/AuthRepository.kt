package com.example.nutriscan.repository

import com.example.nutriscan.datastore.UserPreferences
import kotlinx.coroutines.flow.Flow

class AuthRepository(
    private val pref: UserPreferences
) {

    // Simpan akun (register)
    suspend fun daftarPengguna(email: String, password: String) {
        pref.saveUser(email, password)
    }

    // Login cek kecocokan email & password
    suspend fun login(email: String, password: String): Boolean {
        val savedEmail = pref.getEmail()
        val savedPassword = pref.getPassword()

        return (email == savedEmail && password == savedPassword)
    }

    // Status login
    suspend fun simpanStatusLogin(isLoggedIn: Boolean) {
        pref.saveLoginStatus(isLoggedIn)
    }

    fun ambilStatusLogin(): Flow<Boolean> {
        return pref.loginStatusFlow
    }

    // Logout
    suspend fun logout() {
        pref.saveLoginStatus(false)
    }
}