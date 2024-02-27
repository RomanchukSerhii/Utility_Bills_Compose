package com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.components.BodyTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.PrimaryButton
import com.serhiiromanchuk.utilitybills.presentation.getApplicationComponent
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation.BillGenerationUiState.ServiceItemState
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation.components.AddUtilityServiceCard
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation.components.BillTopBar
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation.components.ServiceItem

@Composable
fun BillScreenRoot(
    modifier: Modifier = Modifier,
    billId: Long,
    onBackPressed: () -> Unit,
    onEditServiceClick: (id: Long, billCreatorId: Long) -> Unit,
    onAddServiceClick: (billCreatorId: Long) -> Unit
) {
    val component = getApplicationComponent()
        .getHomeScreenComponentFactory()
        .create(billId)
    val viewModel: BillGenerationViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.navigationEvent.collect { navigationEvent ->
            when (navigationEvent) {
                is BillGenerationViewModel.NavigationEvent.OnAddService -> {
                    onAddServiceClick(navigationEvent.billCreatorId)
                }

                BillGenerationViewModel.NavigationEvent.OnBack -> {
                    onBackPressed()
                }

                BillGenerationViewModel.NavigationEvent.OnCreateBill -> TODO()
                is BillGenerationViewModel.NavigationEvent.OnEditService -> TODO()
            }
        }
    }

    BillScreen(
        modifier = modifier,
        screenState = screenState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun BillScreen(
    modifier: Modifier = Modifier,
    screenState: State<BillGenerationUiState>,
    onEvent: (BillGenerationUiEvent) -> Unit
) {
    val currentState = screenState.value
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            BillTopBar(
                billItem = currentState.bill,
                onEvent = onEvent
            )
        },
        bottomBar = {
            PrimaryButton(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                text = stringResource(R.string.create_bill),
                onClick = { BillGenerationUiEvent.Submit },
                enabled = currentState.isNextButtonEnabled
            )
        }
    ) { paddingValues ->
        ServiceItemList(
            modifier = modifier.padding(paddingValues),
            billCreatorId = currentState.bill.id,
            serviceStateList = currentState.list,
            onEvent = onEvent
        )
    }
}

@Composable
private fun ServiceItemList(
    modifier: Modifier = Modifier,
    billCreatorId: Long,
    serviceStateList: List<ServiceItemState>,
    onEvent: (BillGenerationUiEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
    ) {
        item {
            BodyTextOnSurface(
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_small),
                    top = dimensionResource(id = R.dimen.padding_medium)
                ),
                text = stringResource(R.string.choose_utility_service_title)
            )
        }

        items(serviceStateList, key = { it.utilityServiceItem.id }) { serviceState ->
            ServiceItem(serviceState = serviceState, onEvent = onEvent)
        }

        item {
            AddUtilityServiceCard(
                modifier = Modifier.clickable {
                    onEvent(BillGenerationUiEvent.AddUtilityService(billCreatorId))
                }
            )
        }
    }
}