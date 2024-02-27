package com.serhiiromanchuk.utilitybills.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.serhiiromanchuk.utilitybills.utils.UNDEFINED_ID

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    chooseBillScreenContent: @Composable () -> Unit,
    editPackageScreenContent: @Composable (String, Long) -> Unit,
    addBillScreenContent: @Composable () -> Unit,
    billGenerationScreenContent: @Composable (Long) -> Unit,
    insertServiceScreenContent: @Composable (id: Long, billCreatorId: Long) -> Unit,
    billsArchiveScreenContent: @Composable () -> Unit,
    billScreenContent: @Composable () -> Unit,
    billDetailsScreenContent: @Composable () -> Unit,

    ) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.StartScreen.route
    ) {
        startScreenNavGraph(
            chooseBillScreenContent = chooseBillScreenContent,
            addBillScreenContent = addBillScreenContent,
            editPackageScreenContent = editPackageScreenContent
        )
        billHomeScreenNavGraph(
            navController = navHostController,
            billScreen = billScreenContent,
            billDetailsScreen = billDetailsScreenContent
        )
        composable(
            route = Screen.BillGenerationScreen.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_BILL_ID) {
                    type = NavType.LongType
                }
            )
        ) {
            val id = it.arguments?.getLong(Screen.KEY_BILL_ID) ?: UNDEFINED_ID
            billGenerationScreenContent(id)
        }
        composable(
            route = Screen.InsertServiceScreen.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_SERVICE_ID) {
                    type = NavType.LongType
                },
                navArgument(name = Screen.KEY_BILL_ID) {
                    type = NavType.LongType
                }
            )
        ) {
            val id = it.arguments?.getLong(Screen.KEY_SERVICE_ID) ?: UNDEFINED_ID
            val billCreatorId = it.arguments?.getLong(Screen.KEY_BILL_ID) ?: UNDEFINED_ID
            insertServiceScreenContent(id, billCreatorId)
        }
        composable(Screen.BillsArchiveScreen.route) {
            billsArchiveScreenContent()
        }
    }
}