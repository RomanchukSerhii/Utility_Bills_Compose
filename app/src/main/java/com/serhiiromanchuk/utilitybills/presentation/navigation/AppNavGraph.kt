package com.serhiiromanchuk.utilitybills.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    startScreenContent: @Composable () -> Unit,
    homeScreenContent: @Composable () -> Unit,
    insertServiceScreenContent: @Composable (Int) -> Unit,
    billsArchiveScreenContent: @Composable () -> Unit,
    billScreenContent: @Composable () -> Unit,
    billDetailsScreenContent: @Composable () -> Unit,

    ) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.StartScreen.route) {
            startScreenContent()
        }
        composable(Screen.HomeScreen.route) {
            homeScreenContent()
        }
        composable(
            route = Screen.InsertServiceScreen.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_SERVICE_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            val utilityServiceId = it.arguments?.getInt(Screen.KEY_SERVICE_ID) ?: -1
            insertServiceScreenContent(utilityServiceId)
        }
        composable(Screen.BillsArchiveScreen.route) {
            billsArchiveScreenContent()
        }
        composable(Screen.BillScreen.route) {
            billScreenContent()
        }
        composable(Screen.BillDetails.route) {
            billDetailsScreenContent()
        }
    }
}