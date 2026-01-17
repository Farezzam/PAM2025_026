package com.example.nutriscan.view.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nutriscan.view.HalamanAbout
import com.example.nutriscan.view.HalamanChart
import com.example.nutriscan.view.HalamanEdit
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
            HalamanHome(
                foodVM = foodVM,
                keInput = { navController.navigate("input") },
                keHistory = { navController.navigate("history") },
                keChart = { navController.navigate("chart") },
                keAbout = { navController.navigate("about") },
                onLogout = {
                    authVM.logout()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                        launchSingleTop = true
                    }
                }
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
            LaunchedEffect (Unit) {
                foodVM.ambilRiwayat()
            }

            HalamanHistory(
                foodVM = foodVM,
                kembaliKeHome = {
                    navController.popBackStack()
                },
                keEdit = { foodId ->
                    navController.navigate("edit/$foodId")
                }
            )
        }

        composable(
            route = "edit/{foodId}",
            arguments = listOf(navArgument("foodId") { type = NavType.IntType })
        ) { backStackEntry ->
            val foodId = backStackEntry.arguments?.getInt("foodId") ?: 0
            HalamanEdit (
                foodId = foodId,
                foodVM = foodVM,
                kembali = { navController.popBackStack() }
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