package com.example.nutriscan.room

import android.app.Application

class NutriScanApp : Application() {
    val database: FoodDatabase by lazy { FoodDatabase.getDatabase(this) }
}