package com.serhiiromanchuk.utilitybills.presentation.screen.bill

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.components.BodyTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.CardOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.PrimaryButton
import com.serhiiromanchuk.utilitybills.presentation.getApplicationComponent
import com.serhiiromanchuk.utilitybills.presentation.screen.bill.BillUiState.ServiceItemState
import com.serhiiromanchuk.utilitybills.presentation.screen.bill.components.BillTopBar
import com.serhiiromanchuk.utilitybills.presentation.screen.bill.components.ServiceItem

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
    val viewModel: BillViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.navigationEvent.collect { navigationEvent ->
            when (navigationEvent) {
                is BillViewModel.NavigationEvent.OnAddService -> {
                    onAddServiceClick(navigationEvent.billCreatorId)
                }

                BillViewModel.NavigationEvent.OnBack -> {
                    onBackPressed()
                }

                BillViewModel.NavigationEvent.OnCreateBill -> TODO()
                is BillViewModel.NavigationEvent.OnEditService -> TODO()
            }
        }
    }

    BillScreen(
        modifier = modifier,
        screenState = screenState,
        onEvent = viewModel::onEvent
    )

//    Scaffold(
//        topBar = {
//            HomeScreenHeader(
//                billItem = fakeBillItem,
//                addressList = listOf("вул. Грушевського 23, кв. 235"),
//                onCardNumberEditClick = { /*TODO*/ },
//                changeBillAddress = {},
//                onAddNewAddressClick = {}
//            )
//        },
//        bottomBar = {
//            HomeScreenBottomBar(
//                onButtonClick = { /*TODO*/ },
//                enabled = screenState.value.isCreateBillEnabled
//            )
//        }
//    ) {
//        HomeScreenContent(
//            modifier = modifier.padding(it),
//            screenState = screenState,
//            onPreviousValueChange = { id, value ->
//                viewModel.meterValueChange(id, value, MeterValueType.PREVIOUS)
//            },
//            onCurrentValueChange = { id, value ->
//                viewModel.meterValueChange( id, value, MeterValueType.CURRENT)
//            },
//            isServiceEnabled = { id, isChecked ->
//                viewModel.changeServiceCheckedState(id, isChecked)
//            },
//            onEditServiceClick = { id -> onEditServiceClick(id, fakeBillItem.id) },
//            onAddUtilityServiceClick = { onAddUtilityServiceClick(fakeBillItem.id) }
//        )
//    }
}

@Composable
private fun BillScreen(
    modifier: Modifier = Modifier,
    screenState: State<BillUiState>,
    onEvent: (BillUiEvent) -> Unit
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
                text = stringResource(R.string.next),
                onClick = { BillUiEvent.Submit },
                enabled = currentState.isNextButtonEnabled
            )
        }
    ) { paddingValues ->
        ServiceItems(
            modifier = modifier.padding(paddingValues),
            billCreatorId = currentState.bill.id,
            serviceStateList = currentState.list,
            onEvent = onEvent
        )
    }
}

//@Composable
//private fun HomeScreenContent(
//    modifier: Modifier = Modifier,
//    screenState: State<BillUiState>,
//    onPreviousValueChange: (id: Long, value: String) -> Unit,
//    onCurrentValueChange: (id: Long, value: String) -> Unit,
//    isServiceEnabled: (id: Long, isChecked: Boolean) -> Unit,
//    onEditServiceClick: (id: Long) -> Unit,
//    onAddUtilityServiceClick: () -> Unit
//) {
//    ServiceItems(
//        modifier = modifier,
//        serviceItems = screenState.value.list,
//        onPreviousValueChange = onPreviousValueChange,
//        onCurrentValueChange = onCurrentValueChange,
//        onEditServiceClick = onEditServiceClick,
//        isServiceEnabled = isServiceEnabled,
//        onAddUtilityServiceClick = onAddUtilityServiceClick
//    )
//}

@Composable
private fun ServiceItems(
    modifier: Modifier = Modifier,
    billCreatorId: Long,
    serviceStateList: List<ServiceItemState>,
    onEvent: (BillUiEvent) -> Unit,
//    onPreviousValueChange: (id: Long, value: String) -> Unit,
//    onCurrentValueChange: (id: Long, value: String) -> Unit,
//    isServiceEnabled: (id: Long, isChecked: Boolean) -> Unit,
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
            AddUtilityServiceCard(modifier = Modifier.clickable {
                onEvent(BillUiEvent.AddUtilityService(billCreatorId))
            })
        }
    }
}

@Composable
private fun AddUtilityServiceCard(
    modifier: Modifier = Modifier
) {
    CardOnSurface {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.width_small)))
            BodyTextOnSurface(
                text = "Додати комунальну послугу",
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun HomeScreenBottomBar(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    enabled: Boolean
) {
    PrimaryButton(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_large)),
        text = "Сформувати рахунок",
        onClick = onButtonClick,
        enabled = enabled
    )
}