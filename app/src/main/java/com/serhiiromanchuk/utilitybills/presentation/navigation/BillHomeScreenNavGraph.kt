package com.serhiiromanchuk.utilitybills.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.serhiiromanchuk.utilitybills.presentation.getApplicationComponent
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_details.SharedBillViewModel
import com.serhiiromanchuk.utilitybills.utils.NOT_FOUND_ID
import com.serhiiromanchuk.utilitybills.utils.sharedViewModel

fun NavGraphBuilder.billHomeScreenNavGraph(
    navController: NavHostController,
    billScreen: @Composable () -> Unit,
    billDetailsScreen: @Composable () -> Unit,
){
    navigation(
        startDestination = Screen.BillScreen.route,
        route = Screen.BillHomeScreen.route
    ) {
        composable(route = Screen.BillScreen.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_BILL_ID) {
                    type = NavType.LongType
                },
                navArgument(name = Screen.KEY_MONTH) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            getBillUiState(entry = entry, navController = navController)
            billScreen()
        }

        composable(
            route = Screen.BillDetailsScreen.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_BILL_ID) {
                    type = NavType.LongType
                },
                navArgument(name = Screen.KEY_MONTH) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            getBillUiState(entry = entry, navController = navController)
            billDetailsScreen()
        }
    }
}
@Composable
private fun getBillUiState(entry: NavBackStackEntry, navController: NavHostController) {
    val billId = entry.arguments?.getLong(Screen.KEY_BILL_ID) ?: NOT_FOUND_ID
    val month = entry.arguments?.getString(Screen.KEY_MONTH) ?: ""

    val component = getApplicationComponent()
        .getBillHomeScreenComponentFactory()
        .create(billId, month)

    val viewModel = entry.sharedViewModel<SharedBillViewModel>(
        navController = navController,
        factory = component.getViewModelFactory()
    )
}