package com.example.nutriscan.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserPreferences(private val context: Context) {

    companion object {
        private val Context.dataStore by preferencesDataStore("user_prefs")

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val LOGIN_STATUS_KEY = booleanPreferencesKey("login_status")
    }

    // Simpan email & password (register)
    suspend fun saveUser(email: String, password: String) {
        context.dataStore.edit { prefs ->
            prefs[EMAIL_KEY] = email
            prefs[PASSWORD_KEY] = password
        }
    }

    suspend fun getEmail(): String {
        val prefs = context.dataStore.data.map { it[EMAIL_KEY] ?: "" }
        return prefs.first()
    }

    suspend fun getPassword(): String {
        val prefs = context.dataStore.data.map { it[PASSWORD_KEY] ?: "" }
        return prefs.first()
    }

    suspend fun saveLoginStatus(isLoggedIn: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[LOGIN_STATUS_KEY] = isLoggedIn
        }
    }

    val loginStatusFlow: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[LOGIN_STATUS_KEY] ?: false
    }
}