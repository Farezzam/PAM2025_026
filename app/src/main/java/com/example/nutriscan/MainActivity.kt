package com.example.nutriscan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.nutriscan.datastore.UserPreferences
import com.example.nutriscan.repository.AuthRepository
import com.example.nutriscan.repository.FoodRepository
import com.example.nutriscan.ui.theme.NutriScanTheme
import com.example.nutriscan.view.uicontroller.PetaNavigasi
import com.example.nutriscan.viewmodel.AuthViewModel
import com.example.nutriscan.viewmodel.ChartViewModel
import com.example.nutriscan.viewmodel.FoodViewModel
import com.example.nutriscan.room.FoodDatabase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // Mengambil context yang benar untuk Compose
            val context = LocalContext.current

            // --- 1. INIT DATASTORE & DATABASE ---
            // UserPreferences biasanya butuh context untuk DataStore
            val userPrefs = remember { UserPreferences(context) }
            val db = remember { FoodDatabase.getDatabase(context) }
            val dao = remember { db.foodDao() }

            // --- 2. INIT REPOSITORY ---
            // AuthRepository sekarang menerima userPrefs, bukan context
            val authRepo = remember { AuthRepository(userPrefs) }
            val foodRepo = remember { FoodRepository(dao) }

            // --- 3. INIT VIEWMODEL ---
            // Menginisialisasi ViewModel secara manual sesuai constructor di file yang kamu kirim
            val authVM = remember { AuthViewModel(authRepo) }
            val foodVM = remember { FoodViewModel(foodRepo) }
            val chartVM = remember { ChartViewModel(foodRepo) }

            val navController = rememberNavController()

            NutriScanTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { padding ->
                    PetaNavigasi(
                        navController = navController,
                        authVM = authVM,
                        foodVM = foodVM,
                        chartVM = chartVM,
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}

