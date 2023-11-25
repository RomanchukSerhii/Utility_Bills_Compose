package com.serhiiromanchuk.utilitybills.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    startScreenContent: @Composable () -> Unit,
    mainScreenContent: @Composable () -> Unit,
    insertServiceScreenContent: @Composable () -> Unit,
    billsArchiveScreenContent: @Composable () -> Unit,
    billScreenContent: @Composable () -> Unit,
    billDetailsScreenContent: @Composable () -> Unit,

    ) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.StartScreen.route
    ) {
        composable(Screen.StartScreen.route) {
            startScreenContent()
        }
        composable(Screen.MainScreen.route) {
            mainScreenContent()
        }
        composable(Screen.InsertServiceScreen.route) {
            insertServiceScreenContent()
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