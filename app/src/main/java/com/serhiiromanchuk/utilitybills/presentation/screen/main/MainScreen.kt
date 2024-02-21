package com.serhiiromanchuk.utilitybills.presentation.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.navigation.AppNavGraph
import com.serhiiromanchuk.utilitybills.presentation.navigation.Screen
import com.serhiiromanchuk.utilitybills.presentation.navigation.rememberNavigationState
import com.serhiiromanchuk.utilitybills.presentation.screen.home.HomeScreen
import com.serhiiromanchuk.utilitybills.presentation.screen.insert_service.InsertUtilityServiceScreen
import com.serhiiromanchuk.utilitybills.presentation.screen.start.add_bill.AddBillScreen
import com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill.ChooseBillScreenRoot
import com.serhiiromanchuk.utilitybills.presentation.screen.start.edit_package.EditPackageScreenRoot

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val navigationState = rememberNavigationState()

    AppNavGraph(
        navHostController = navigationState.navHostController,
        addBillScreenContent = {
            AddBillScreen(
                onBackPressed = { navigationState.navHostController.popBackStack() }
            )
        },
        chooseBillScreenContent = {
            ChooseBillScreenRoot(
                onAddBillClick = { navigationState.navigateTo(Screen.AddBillScreen.route) },
                onBillItemClick = { navigationState.navigateToHomeScreen(billId = it)},
                onEditPackageClick = { address, id ->
                    navigationState.navigateToEditPackageScreen(address, id)
                }
            )
        },
        editPackageScreenContent = { billAddress, billId ->
            EditPackageScreenRoot(
                billAddress = billAddress,
                billId = billId,
                onBackPressed = { navigationState.navHostController.popBackStack() }
            )
        },
        homeScreenContent = { billId ->
            HomeScreen(
                billId = billId,
                onEditServiceClick = { id, billCreatorId ->
                    navigationState.navigateToInsertServiceScreen(id, billCreatorId)
                },
                onAddUtilityServiceClick = { billCreatorId ->
                    navigationState.navigateToInsertServiceScreen(billCreatorId = billCreatorId)
                }
            )
        },
        insertServiceScreenContent = { utilityServiceId, billCreatorId ->
            InsertUtilityServiceScreen(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                utilityServiceId = utilityServiceId,
                billCreatorId = billCreatorId,
                onBackPressed = { navigationState.navHostController.popBackStack() }
            )
        },
        billsArchiveScreenContent = { /*TODO*/ },
        billScreenContent = { /*TODO*/ }) {

    }

}

@Composable
fun AddNewBillScreen(
    modifier: Modifier = Modifier
) {

}