package com.serhiiromanchuk.utilitybills.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.serhiiromanchuk.utilitybills.utils.UNDEFINED_ID

fun NavGraphBuilder.startScreenNavGraph(
    chooseBillScreenContent: @Composable () -> Unit,
    addBillScreenContent: @Composable () -> Unit,
    editPackageScreenContent: @Composable (String, Long) -> Unit
) {
    navigation(
        startDestination = Screen.ChooseBillScreen.route,
        route = Screen.StartScreen.route
    ) {
        composable(Screen.ChooseBillScreen.route) { chooseBillScreenContent() }
        composable(Screen.AddBillScreen.route) { addBillScreenContent() }
        composable(
            route = Screen.EditPackageScreen.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_PACKAGE_NAME) {
                    type = NavType.StringType
                },
                navArgument(name = Screen.KEY_PACKAGE_ID) {
                    type = NavType.LongType
                }
            )
        ) {
            val packageName = it.arguments?.getString(Screen.KEY_PACKAGE_NAME) ?: ""
            val billId = it.arguments?.getLong(Screen.KEY_PACKAGE_ID) ?: UNDEFINED_ID
            editPackageScreenContent(packageName, billId)
        }
    }
}