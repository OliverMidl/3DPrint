package com.example.a3dprint

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "zakazky") {
        composable("zakazky") {
            ZakazkyScreen(onNavigateToFilamenty = {
                navController.navigate("filamenty")
            })
        }
        composable("filamenty") {
            FilamentyScreen(onNavigateToZakazky = {
                navController.navigate("zakazky")
            })
        }
    }
}