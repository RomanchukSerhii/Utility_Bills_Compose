package com.serhiiromanchuk.utilitybills.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.serhiiromanchuk.utilitybills.utils.NOT_FOUND_ID

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

    fun navigateToInsertServiceScreen(utilityServiceId: Long = NOT_FOUND_ID, billCreatorId: Long) {
        navHostController.navigate(Screen.InsertServiceScreen.getRoutWithArgs(utilityServiceId, billCreatorId))
    }

    fun navigateToEditPackageScreen(packageName: String, billId: Long) {
        navHostController.navigate(Screen.EditPackageScreen.getRoutWithArgs(packageName, billId))
    }

    fun navigateToBillGenerationScreen(billId: Long) {
        navHostController.navigate(Screen.BillGenerationScreen.getRoutWithArgs(billId))
    }

    fun navigateToBillDescriptionScreen(billId: Long) {
        navHostController.navigate(Screen.BillDescriptionScreen.getRoutWithArgs(billId))
    }

    fun navigateToBillDetailsScreen(billId: Long) {
        navHostController.navigate(Screen.BillDetailsScreen.getRoutWithArgs(billId))
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