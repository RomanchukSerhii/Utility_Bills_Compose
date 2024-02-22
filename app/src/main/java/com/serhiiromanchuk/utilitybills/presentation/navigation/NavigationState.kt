package com.serhiiromanchuk.utilitybills.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.serhiiromanchuk.utilitybills.utils.UNDEFINED_ID

class NavigationState(
    val navHostController: NavHostController
) {

    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToInsertServiceScreen(utilityServiceId: Long = UNDEFINED_ID, billCreatorId: Long) {
        navHostController.navigate(Screen.InsertServiceScreen.getRoutWithArgs(utilityServiceId, billCreatorId))
    }

    fun navigateToEditPackageScreen(packageName: String, billId: Long) {
        navHostController.navigate(Screen.EditPackageScreen.getRoutWithArgs(packageName, billId))
    }

    fun navigateToBillScreen(billId: Long) {
        navHostController.navigate(Screen.BillScreen.getRoutWithArgs(billId))
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}