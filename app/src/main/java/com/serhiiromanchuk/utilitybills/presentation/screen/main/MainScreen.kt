package com.serhiiromanchuk.utilitybills.presentation.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.serhiiromanchuk.utilitybills.R
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
                onEditServiceClick = { id, address ->  navigationState.navigateToInsertServiceScreen(id, address) },
                onAddUtilityServiceClick = { address ->
                    val undefinedId = -1L
                    navigationState.navigateToInsertServiceScreen(undefinedId, address)
                }
            )
        },
        insertServiceScreenContent = { utilityServiceId, address ->
            InsertUtilityServiceScreen(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                utilityServiceId = utilityServiceId,
                address = address,
                onBackPressed = { navigationState.navHostController.popBackStack() }
            )
        },
        billsArchiveScreenContent = { /*TODO*/ },
        billScreenContent = { /*TODO*/ }) {

    }

}