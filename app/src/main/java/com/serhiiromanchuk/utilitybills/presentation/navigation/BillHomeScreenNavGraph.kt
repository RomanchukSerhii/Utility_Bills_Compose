package com.serhiiromanchuk.utilitybills.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.serhiiromanchuk.utilitybills.presentation.getApplicationComponent
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_details.BillDescriptionScreen
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_details.BillDetailsScreen
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_details.SharedBillViewModel
import com.serhiiromanchuk.utilitybills.utils.NOT_FOUND_ID
import com.serhiiromanchuk.utilitybills.utils.sharedViewModel

fun NavGraphBuilder.billHomeScreenNavGraph(
    navController: NavHostController
){
    navigation(
        startDestination = Screen.BillDescriptionScreen.route,
        route = Screen.BillScreen.route
    ) {
        composable(route = Screen.BillDescriptionScreen.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_BILL_ID) {
                    type = NavType.LongType
                }
            )
        ) { entry ->
            val viewModel = getBillUiState(entry = entry, navController = navController)
            val screenState = viewModel.screenState.collectAsStateWithLifecycle()
            BillDescriptionScreen(screenState = screenState, onEvent = viewModel::onEvent)
        }

        composable(
            route = Screen.BillDetailsScreen.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_BILL_ID) {
                    type = NavType.LongType
                }
            )
        ) { entry ->
            val viewModel = getBillUiState(entry = entry, navController = navController)
            val screenState = viewModel.screenState.collectAsStateWithLifecycle()
            BillDetailsScreen(screenState = screenState, onEvent = viewModel::onEvent)
        }
    }
}
@Composable
private fun getBillUiState(entry: NavBackStackEntry, navController: NavHostController): SharedBillViewModel {
    val billId = entry.arguments?.getLong(Screen.KEY_BILL_ID) ?: NOT_FOUND_ID

    val component = getApplicationComponent()
        .getBillDetailsScreenComponentFactory()
        .create(billId)

    return entry.sharedViewModel<SharedBillViewModel>(
        navController = navController,
        factory = component.getViewModelFactory()
    )
}