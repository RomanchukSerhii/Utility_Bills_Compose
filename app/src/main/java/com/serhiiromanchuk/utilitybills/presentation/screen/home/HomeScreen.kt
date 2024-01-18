package com.serhiiromanchuk.utilitybills.presentation.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.mocks.fakeBillItem
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.presentation.core.components.BodyTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.PrimaryButton
import com.serhiiromanchuk.utilitybills.utils.MeterValueType

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: HomeScreenViewModel = viewModel()

    when (val screenState = viewModel.screenState) {
        is HomeScreenState.Initial -> {}
        is HomeScreenState.Content -> {
            HomeScreenContent(
                modifier = modifier,
                screenState = screenState,
                onPreviousValueChange = { id, value ->
                    viewModel.meterValueChange(id, value, MeterValueType.PREVIOUS)
                },
                onCurrentValueChange = { id, value ->
                    viewModel.meterValueChange( id, value, MeterValueType.CURRENT)
                }
            )
        }
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    screenState: HomeScreenState.Content,
    onPreviousValueChange: (id: Int, value: String) -> Unit,
    onCurrentValueChange: (id: Int, value: String) -> Unit,
) {
    Scaffold(
        topBar = {
            HomeScreenHeader(
                billItem = fakeBillItem,
                addressList = listOf("вул. Грушевського 23, кв. 235"),
                onCardNumberEditClick = { /*TODO*/ },
                changeBillAddress = {},
                onAddNewAddressClick = {}
            )
        },
        bottomBar = {
            HomeScreenBottomBar(
                onButtonClick = { /*TODO*/ },
                enabled = screenState.isCreateBillEnabled
            )
        }
    ) {
        ServiceItems(
            modifier = modifier.padding(it),
            serviceItems = screenState.list,
            onPreviousValueChange = onPreviousValueChange,
            onCurrentValueChange = onCurrentValueChange,
            onEditServiceClick = { /*TODO*/ },
            isEnabled = {},
            onAddUtilityServiceClick = {}
        )
    }
}

@Composable
fun ServiceItems(
    modifier: Modifier = Modifier,
    serviceItems: List<UtilityServiceItem>,
    onPreviousValueChange: (id: Int, value: String) -> Unit,
    onCurrentValueChange: (id: Int, value: String) -> Unit,
    onEditServiceClick: () -> Unit,
    isEnabled: (Boolean) -> Unit,
    onAddUtilityServiceClick: () -> Unit
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
                    top = dimensionResource(id = R.dimen.padding_small)
                ),
                text = "Виберіть комунальні послуги для формування рахунку"
            )
        }
        items(serviceItems, key = { it.id }) { utilityService ->
            ServiceItem(
                utilityService = utilityService,
                onPreviousValueChange = onPreviousValueChange,
                onCurrentValueChange = onCurrentValueChange,
                onEditServiceClick = onEditServiceClick,
                isEnabled = isEnabled
            )
        }
        item {
            AddUtilityServiceCard(onAddUtilityServiceClick = onAddUtilityServiceClick)
        }
    }
}

@Composable
fun AddUtilityServiceCard(
    modifier: Modifier = Modifier,
    onAddUtilityServiceClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onAddUtilityServiceClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
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
fun HomeScreenBottomBar(
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