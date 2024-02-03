package com.serhiiromanchuk.utilitybills.presentation.screen.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.mocks.fakeBillItem
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.BillCardIcon
import com.serhiiromanchuk.utilitybills.presentation.core.components.CardOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.TextOnBillCard
import com.serhiiromanchuk.utilitybills.presentation.core.components.TopBarApp
import com.serhiiromanchuk.utilitybills.presentation.getApplicationComponent
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    onAddBillClick: () -> Unit
) {
    val component = getApplicationComponent()
    val viewModel: StartScreenViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsState()

    Scaffold(
        topBar = {
            TopBarApp(titleId = R.string.utility_bills)
        }
    ) {
        StartScreenContent(
            modifier = modifier.padding(it),
            screenState = screenState.value,
            onAddBillClick = onAddBillClick,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun StartScreenContent(
    modifier: Modifier = Modifier,
    screenState: StartScreenState,
    onAddBillClick: () -> Unit,
    onEvent: (StartScreenEvent) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_extra_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_extra_small)),
    ) {
        item {
            AddNewBill( onAddBillClick = onAddBillClick )
        }
        items(screenState.billList, key = { it.id }) { billItem ->
            BillAddressCard(billAddress = billItem.address)
        }
    }
}

@Composable
private fun AddNewBill(
    modifier: Modifier = Modifier,
    onAddBillClick: () -> Unit
) {
    CardOnSurface(
        modifier = modifier
            .height(170.dp),
        onCardClick = onAddBillClick
    ) {
        BillCardIcon(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            iconId = R.drawable.ic_add_folder,
            contentDescriptionId = R.string.add_bill_address,
            isAddIcon = true
        )
        TextOnBillCard(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.add_bill_address)
        )
    }
}

@Composable
private fun BillAddressCard(
    modifier: Modifier = Modifier,
    billAddress: String
) {
    CardOnSurface(
        modifier = modifier
    ) {
        BillCardIcon(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            iconId = R.drawable.ic_home,
            contentDescriptionId = null
        )
        TextOnBillCard(
            modifier = Modifier.weight(1f),
            text = billAddress
        )
    }
}

@DarkLightPreviews
@Composable
private fun AddNewBillPreview() {
    UtilityBillsTheme() {
        AddNewBill(
            onAddBillClick = {}
        )
    }
}

@DarkLightPreviews
@Composable
private fun StartScreenLayoutPreview() {
    UtilityBillsTheme() {
        StartScreenContent(
            screenState = StartScreenState(
                listOf(
                    fakeBillItem,
                    fakeBillItem.copy(
                        id = 1,
                        address = "вул. Коцюбинського 34, кв. 15"
                    ),
                    fakeBillItem.copy(
                        id =2,
                        address = "вул. Пилипа Орлика 14, кв.3"
                    )
                )
            ),
            onAddBillClick = {},
            onEvent = {}
        )
    }
}