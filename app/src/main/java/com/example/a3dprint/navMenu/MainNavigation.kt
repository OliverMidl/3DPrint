package com.example.a3dprint.navMenu

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a3dprint.Screens.FilamentyScreen
import com.example.a3dprint.Screens.FilamentyScreenDest
import com.example.a3dprint.Screens.FinancieScreen
import com.example.a3dprint.Screens.FinancieScreenDest
import com.example.a3dprint.Screens.ZakazkyScreen
import com.example.a3dprint.Screens.ZakazkyScreenDest
import com.example.a3dprint.Screens.AddFilamentScreen
import com.example.a3dprint.Screens.AddFilamentScreenDest
import com.example.a3dprint.Screens.AddZakazkaScreen
import com.example.a3dprint.Screens.AddZakazkaScreenDest
import com.example.a3dprint.Screens.ZakazkaDetailScreen
import com.example.a3dprint.Screens.ZakazkaDetailScreenDest
import com.example.a3dprint.Screens.FilamentDetailScreen
import com.example.a3dprint.Screens.FilamentDetailScreenDest
import com.example.a3dprint.Screens.StatistikaScreen
import com.example.a3dprint.Screens.StatistikaScreenDest

/**
 * Definuje navigáciu medzi jednotlivými obrazovkami aplikácie.
 * Používa Jetpack Navigation Compose správu navigácie.
 *
 * @param modifier Voliteľný modifier pre NavHost.
 */
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
        /**
         * Navigačná definícia pre obrazovku zákaziek.
         */
        composable(route = ZakazkyScreenDest.route) {
            ZakazkyScreen(
                onNavigateToFilamenty = {
                navController.navigate(route = FilamentyScreenDest.route)
                },
                onNavigateToFinancie = {
                navController.navigate(route = FinancieScreenDest.route)
                },
                onNavigateToAddZakazka = {
                    navController.navigate(route = AddZakazkaScreenDest.route)
                },
                onNavigateToDetailZakazka = { zakazkaId ->
                    navController.navigate("${ZakazkaDetailScreenDest.route}/$zakazkaId")
                }
            )
        }
        /**
         * Navigačná definícia pre obrazovku filamentov.
         */
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
                },
                onNavigateToFilamentDetail = { id ->
                    navController.navigate("${FilamentDetailScreenDest.route}/$id")
                }
            )
        }
        /**
         * Navigačná definícia pre obrazovku financií.
         */
        composable(route = FinancieScreenDest.route) {
            FinancieScreen(
                onNavigateToZakazky = {
                navController.navigate(route = ZakazkyScreenDest.route)
                },
                onNavigateToFilamenty = {
                    navController.navigate(route = FilamentyScreenDest.route)
                },
                onNavigateToStatistics = {
                    navController.navigate(route = StatistikaScreenDest.route)
                }
            )
        }
        /**
         * Navigačná definícia pre obrazovku štatistiky.
         */
        composable(route = StatistikaScreenDest.route) {
            StatistikaScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        /**
         * Navigačná definícia pre obrazovku pridania filamentu.
         */
        composable(route = AddFilamentScreenDest.route) {
            AddFilamentScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        /**
         * Navigačná definícia pre obrazovku pridania zákazky.
         */
        composable(route = AddZakazkaScreenDest.route) {
            AddZakazkaScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        /**
         * Navigačná definícia pre detail filamentu s parametrom ID.
         */
        composable(
            route = FilamentDetailScreenDest.routeWithArgs,
            arguments = listOf(navArgument(FilamentDetailScreenDest.filamentIdArg) {
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            val filamentId = navBackStackEntry.arguments?.getInt(FilamentDetailScreenDest.filamentIdArg)
            filamentId?.let {
                FilamentDetailScreen(
                    filamentId = it,
                    onBack = { navController.popBackStack() },
                )
            }
        }
        /**
         * Navigačná definícia pre detail zákazky s parametrom ID.
         */
        composable(
            route = ZakazkaDetailScreenDest.routeWithArgs,
            arguments = listOf(navArgument(ZakazkaDetailScreenDest.zakazkaIdArg) {
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            val zakazkaId = navBackStackEntry.arguments?.getInt(ZakazkaDetailScreenDest.zakazkaIdArg)

            zakazkaId?.let {
                ZakazkaDetailScreen(
                    zakazkaId = it,
                    onBack = { navController.popBackStack() },
                )
            }
        }
    }
}