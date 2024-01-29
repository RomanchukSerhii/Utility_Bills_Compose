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
    insertServiceScreenContent: @Composable (id: Long, address: String) -> Unit,
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
                    type = NavType.LongType
                },
                navArgument(name = Screen.KEY_SERVICE_ADDRESS) {
                    type = NavType.StringType
                }
            )
        ) {
            val id = it.arguments?.getLong(Screen.KEY_SERVICE_ID) ?: -1L
            val address = it.arguments?.getString(Screen.KEY_SERVICE_ADDRESS) ?: ""
            insertServiceScreenContent(id, address)
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