package com.serhiiromanchuk.utilitybills.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.serhiiromanchuk.utilitybills.presentation.navigation.BillScreen.Companion.KEY_BILL_ID
import com.serhiiromanchuk.utilitybills.presentation.navigation.BillScreen.Companion.KEY_SERVICE_ID
import com.serhiiromanchuk.utilitybills.utils.NOT_FOUND_ID

fun NavGraphBuilder.billNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.BILL,
        startDestination = BillScreen.BillGeneration.route,
    ) {
        composable(
            route = BillScreen.BillGeneration.route,
            arguments = listOf(
                navArgument(name = KEY_BILL_ID) {
                    type = NavType.LongType
                }
            )
        ) {
            val id = it.arguments?.getLong(KEY_BILL_ID) ?: NOT_FOUND_ID
            billGenerationScreen(id)
        }
        composable(
            route = BillScreen.InsertService.route,
            arguments = listOf(
                navArgument(name = KEY_SERVICE_ID) {
                    type = NavType.LongType
                },
                navArgument(name = KEY_BILL_ID) {
                    type = NavType.LongType
                }
            )
        ) {
            val id = it.arguments?.getLong(KEY_SERVICE_ID) ?: NOT_FOUND_ID
            val billCreatorId = it.arguments?.getLong(KEY_BILL_ID) ?: NOT_FOUND_ID
            insertServiceScreen(id, billCreatorId)
        }

    }
}

sealed class BillScreen(val route: String) {

    data object BillGeneration : Screen(ROUTE_BILL_GENERATION_SCREEN) {

        private const val ROUTE_FOR_ARGS = "bill_generation_screen"

        fun getRoutWithArgs(billId: Long): String {
            return "$ROUTE_FOR_ARGS/$billId"
        }
    }

    data object InsertService : Screen(ROUTE_INSERT_SERVICE_SCREEN) {

        private const val ROUTE_FOR_ARGS = "insert_service_screen"

        fun getRoutWithArgs(utilityServiceId: Long, billCreatorId: Long): String {
            return "$ROUTE_FOR_ARGS/$utilityServiceId/$billCreatorId"
        }
    }

    companion object {
        const val KEY_BILL_ID = "bill_id"
        const val KEY_SERVICE_ID = "utility_service_id"

        private const val ROUTE_BILL_GENERATION_SCREEN = "bill_generation_screen/{${KEY_BILL_ID}}"
        private const val ROUTE_INSERT_SERVICE_SCREEN = "insert_service_screen/{${KEY_SERVICE_ID}}/{${KEY_BILL_ID}}"
    }

}