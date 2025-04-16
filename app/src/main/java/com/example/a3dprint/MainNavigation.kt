package com.example.a3dprint

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a3dprint.Screens.FilamentyScreen
import com.example.a3dprint.Screens.FinancieScreen
import com.example.a3dprint.Screens.ZakazkyScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "zakazky") {
        composable("zakazky") {
            ZakazkyScreen(
                onNavigateToFilamenty = {
                navController.navigate("filamenty")
            },
                onNavigateToFinancie = {
                navController.navigate("financie")
            }
            )
        }
        composable("filamenty") {
            FilamentyScreen(
                onNavigateToZakazky = {
                navController.navigate("zakazky")
            },
                onNavigateToFinancie = {
                    navController.navigate("financie")
                }
            )
        }
        composable("financie") {
            FinancieScreen(
                onNavigateToZakazky = {
                navController.navigate("zakazky")
            },
                onNavigateToFilamenty = {
                    navController.navigate("filamenty")
                }
            )
        }
    }
}