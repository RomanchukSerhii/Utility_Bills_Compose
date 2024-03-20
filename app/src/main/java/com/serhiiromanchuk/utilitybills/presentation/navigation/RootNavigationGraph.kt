package com.serhiiromanchuk.utilitybills.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost

@Composable
fun RootNavigationGraph() {
    val navigationState = rememberNavigationState()
    NavHost(
        navController = navigationState.navHostController,
        startDestination = Graph.PACKAGE,
        route = Graph.ROOT
    ) {
        packageNavGraph(navigationState)
        billNavGraph(navigationState)
        billDetailsNavGraph(navigationState)
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val PACKAGE = "package_graph"
    const val BILL = "bill_graph"
    const val BILL_DETAILS = "bill_details_graph"
}