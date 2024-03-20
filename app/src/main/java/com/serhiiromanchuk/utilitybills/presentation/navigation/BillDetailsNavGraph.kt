package com.serhiiromanchuk.utilitybills.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.serhiiromanchuk.utilitybills.presentation.getApplicationComponent
import com.serhiiromanchuk.utilitybills.presentation.navigation.BillDetailsScreen.Companion.KEY_BILL_ID
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_details.BillDescriptionScreen
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_details.BillDetailsScreen
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_details.SharedBillViewModel
import com.serhiiromanchuk.utilitybills.utils.NOT_FOUND_ID
import com.serhiiromanchuk.utilitybills.utils.sharedViewModel

fun NavGraphBuilder.billDetailsNavGraph(
    navController: NavHostController
){
    navigation(
        startDestination = BillDetailsScreen.BillDetails.route,
        route = Graph.BILL_DETAILS,
        arguments = listOf(
            navArgument(name = KEY_BILL_ID) {
                type = NavType.LongType
            }
        )
    ) {
        composable(route = BillDetailsScreen.BillDetails.route
        ) { entry ->
            val parentEntry = remember(entry) { navController.getBackStackEntry(Graph.BILL_DETAILS) }
            val viewModel = getBillUiState(entry = parentEntry, navController = navController)
            val screenState = viewModel.screenState.collectAsStateWithLifecycle()
            BillDescriptionScreen(screenState = screenState, onEvent = viewModel::onEvent)
        }

        composable(
            route = BillDetailsScreen.BillDetailsSub.route,
            arguments = listOf(
                navArgument(name = KEY_BILL_ID) {
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
    val billId = entry.arguments?.getLong(KEY_BILL_ID) ?: NOT_FOUND_ID

    val component = getApplicationComponent()
        .getBillDetailsScreenComponentFactory()
        .create(billId)

    return entry.sharedViewModel<SharedBillViewModel>(
        navController = navController,
        factory = component.getViewModelFactory()
    )
}

sealed class BillDetailsScreen(val route: String) {
    data object BillDetails : BillDetailsScreen(ROUTE_BILL_DETAILS_SCREEN)

    data object BillDetailsSub : BillDetailsScreen(ROUTE_BILL_DETAILS_SCREEN_SUB)

    companion object {
        const val KEY_BILL_ID = "bill_id"

        private const val ROUTE_BILL_DETAILS_SCREEN = "bill_details_screen"
        private const val ROUTE_BILL_DETAILS_SCREEN_SUB = "bill_details_screen_sub"
    }
}