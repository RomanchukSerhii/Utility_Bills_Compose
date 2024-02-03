package com.serhiiromanchuk.utilitybills.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.startScreenNavGraph(
    chooseBillScreenContent: @Composable () -> Unit,
    addBillScreenContent: @Composable () -> Unit
) {
    navigation(
        startDestination = Screen.ChooseBillScreen.route,
        route = Screen.StartScreen.route
    ) {
        composable(Screen.ChooseBillScreen.route) { chooseBillScreenContent() }
        composable(Screen.AddBillScreen.route) { addBillScreenContent() }
    }
}