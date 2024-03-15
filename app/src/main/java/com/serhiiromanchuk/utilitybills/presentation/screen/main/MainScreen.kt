package com.serhiiromanchuk.utilitybills.presentation.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.navigation.AppNavGraph
import com.serhiiromanchuk.utilitybills.presentation.navigation.Screen
import com.serhiiromanchuk.utilitybills.presentation.navigation.rememberNavigationState
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation.BillScreenRoot
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package.AddBillScreenRoute
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageScreenRoot
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.edit_package.EditPackageScreenRoot
import com.serhiiromanchuk.utilitybills.presentation.screen.insert_service.InsertServiceScreenRoot

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val navigationState = rememberNavigationState()

    AppNavGraph(
        navHostController = navigationState.navHostController,
        addBillScreenContent = {
            AddBillScreenRoute(
                onBackPressed = { navigationState.navHostController.popBackStack() }
            )
        },
        chooseBillScreenContent = {
            ChoosePackageScreenRoot(
                onAddBillClick = { navigationState.navigateTo(Screen.AddBillScreen.route) },
                onBillItemClick = { navigationState.navigateToBillGenerationScreen(billId = it)},
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
        billGenerationScreenContent = { billId ->
            BillScreenRoot(
                billPackageId = billId,
                onEditServiceClick = { id, billCreatorId ->
                    navigationState.navigateToInsertServiceScreen(id, billCreatorId)
                },
                onAddServiceClick = { billCreatorId ->
                    navigationState.navigateToInsertServiceScreen(billCreatorId = billCreatorId)
                },
                onBackPressed = { navigationState.navHostController.popBackStack() },
                onSubmitClick = { navigationState.navigateToBillDescriptionScreen(it)}
            )
        },
        insertServiceScreenContent = { utilityServiceId, billCreatorId ->
            InsertServiceScreenRoot(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                utilityServiceId = utilityServiceId,
                billCreatorId = billCreatorId,
                onBackPressed = { navigationState.navHostController.popBackStack() }
            )
        },
        billDetailsScreenContent = {

        },
        billsArchiveScreenContent = {},
        billScreenContent = {}
    )
}

@Composable
fun AddNewBillScreen(
    modifier: Modifier = Modifier
) {

}