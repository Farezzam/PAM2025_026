package com.example.nutriscan.view.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nutriscan.view.HalamanAbout
import com.example.nutriscan.view.HalamanChart
import com.example.nutriscan.view.HalamanHistory
import com.example.nutriscan.view.HalamanHome
import com.example.nutriscan.view.HalamanInput
import com.example.nutriscan.view.HalamanLogin
import com.example.nutriscan.view.HalamanOnboarding
import com.example.nutriscan.view.HalamanRegister
import com.example.nutriscan.viewmodel.AuthViewModel
import com.example.nutriscan.viewmodel.ChartViewModel
import com.example.nutriscan.viewmodel.FoodViewModel

@Composable
fun PetaNavigasi(
    navController: NavHostController,
    authVM: AuthViewModel,
    foodVM: FoodViewModel,
    chartVM: ChartViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {

        // ONBOARDING
        composable("onboarding") {
            HalamanOnboarding (
                onMulai = { navController.navigate("login") }
            )
        }

        // LOGIN
        composable("login") {
            HalamanLogin (
                authVM = authVM,
                onLoginBerhasil = { navController.navigate("home") },
                keRegister = { navController.navigate("register") }
            )
        }

        // REGISTER
        composable("register") {
            HalamanRegister (
                authVM = authVM,
                keLogin = { navController.navigate("login") }
            )
        }

        // HALAMAN UTAMA
        composable("home") {
            foodVM.ambilKaloriHariIni()
            HalamanHome (
                foodVM = foodVM,
                keInput = { navController.navigate("input") },
                keHistory = { navController.navigate("history") },
                keChart = { navController.navigate("chart") },
                keAbout = { navController.navigate("about") }
            )
        }

        // INPUT MAKANAN
        composable("input") {
            HalamanInput (
                foodVM = foodVM,
                kembaliKeHome = { navController.navigate("home") }
            )
        }

        // RIWAYAT MAKANAN
        composable("history") {
            foodVM.ambilRiwayat()
            HalamanHistory(
                foodVM = foodVM,
                kembaliKeHome = { navController.navigate("home") }
            )
        }

        // CHART 7 HARI
        composable("chart") {
            chartVM.ambilKaloriMingguan()
            HalamanChart(
                chartVM = chartVM,
                kembaliKeHome = { navController.navigate("home") }
            )
        }

        // ABOUT PAGE
        composable("about") {
            HalamanAbout (
                kembaliKeHome = { navController.navigate("home") }
            )
        }
    }
}