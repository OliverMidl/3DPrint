package com.example.a3dprint.navMenu

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a3dprint.Screens.FilamentyScreen
import com.example.a3dprint.Screens.FilamentyScreenDest
import com.example.a3dprint.Screens.FinancieScreen
import com.example.a3dprint.Screens.FinancieScreenDest
import com.example.a3dprint.Screens.ZakazkyScreen
import com.example.a3dprint.Screens.ZakazkyScreenDest
import com.example.financie.ui.AddFilamentScreen
import com.example.financie.ui.AddFilamentScreenDest


@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ZakazkyScreenDest.route,
        modifier = modifier
    ) {
        composable(route = ZakazkyScreenDest.route) {
            ZakazkyScreen(
                onNavigateToFilamenty = {
                navController.navigate(route = FilamentyScreenDest.route)
            },
                onNavigateToFinancie = {
                navController.navigate(route = FinancieScreenDest.route)
            }
            )
        }
        composable(route = FilamentyScreenDest.route) {
            FilamentyScreen(
                onNavigateToZakazky = {
                navController.navigate(route = ZakazkyScreenDest.route)
                },
                onNavigateToFinancie = {
                    navController.navigate(route = FinancieScreenDest.route)
                },
                onNavigateToAddFilament = {
                    navController.navigate(route = AddFilamentScreenDest.route)
                }
            )
        }
        composable(route = FinancieScreenDest.route) {
            FinancieScreen(
                onNavigateToZakazky = {
                navController.navigate(route = ZakazkyScreenDest.route)
            },
                onNavigateToFilamenty = {
                    navController.navigate(route = FilamentyScreenDest.route)
                }
            )
        }
        composable(route = AddFilamentScreenDest.route) {
            AddFilamentScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}