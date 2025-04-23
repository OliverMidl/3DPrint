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
            },
                onNavigateToAddZakazka = {
                    navController.navigate(route = AddZakazkaScreenDest.route)
            },

                onNavigateToDetailZakazka = { zakazkaId ->
                    navController.navigate("${ZakazkaDetailScreenDest.route}/$zakazkaId")
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
                },
                onNavigateToFilamentDetail = { id ->
                    navController.navigate("${FilamentDetailScreenDest.route}/$id")
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
        composable(route = AddZakazkaScreenDest.route) {
            AddZakazkaScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
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