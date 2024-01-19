package com.serhiiromanchuk.utilitybills.presentation.screen.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.serhiiromanchuk.utilitybills.presentation.navigation.AppNavGraph
import com.serhiiromanchuk.utilitybills.presentation.navigation.rememberNavigationState
import com.serhiiromanchuk.utilitybills.presentation.screen.home.HomeScreen
import com.serhiiromanchuk.utilitybills.presentation.screen.insertservice.InsertUtilityServiceScreen

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val navigationState = rememberNavigationState()

    AppNavGraph(
        navHostController = navigationState.navHostController,
        startScreenContent = { /*TODO*/ },
        homeScreenContent = {
            HomeScreen(
                onEditServiceClick = { navigationState.navigateToInsertServiceScreen(it) },
                onAddUtilityServiceClick = {
                    val undefinedId = -1
                    navigationState.navigateToInsertServiceScreen(undefinedId)
                }
            )
        },
        insertServiceScreenContent = { utilityServiceId ->
            InsertUtilityServiceScreen(utilityServiceId = utilityServiceId)
        },
        billsArchiveScreenContent = { /*TODO*/ },
        billScreenContent = { /*TODO*/ }) {

    }

}