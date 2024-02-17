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
    chooseBillScreenContent: @Composable () -> Unit,
    editPackageScreenContent: @Composable (String) -> Unit,
    addBillScreenContent: @Composable () -> Unit,
    homeScreenContent: @Composable () -> Unit,
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
        composable(Screen.HomeScreen.route) {
            homeScreenContent()
        }
        composable(
            route = Screen.InsertServiceScreen.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_SERVICE_ID) {
                    type = NavType.LongType
                },
                navArgument(name = Screen.KEY_BILL_CREATOR_ID) {
                    type = NavType.LongType
                }
            )
        ) {
            val id = it.arguments?.getLong(Screen.KEY_SERVICE_ID) ?: -1L
            val billCreatorId = it.arguments?.getLong(Screen.KEY_BILL_CREATOR_ID) ?: -1L
            insertServiceScreenContent(id, billCreatorId)
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