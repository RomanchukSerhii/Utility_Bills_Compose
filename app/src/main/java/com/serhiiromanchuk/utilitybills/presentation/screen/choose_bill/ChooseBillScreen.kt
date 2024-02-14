package com.serhiiromanchuk.utilitybills.presentation.screen.choose_bill

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.mocks.fakeBillItem
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.TopBarApp
import com.serhiiromanchuk.utilitybills.presentation.getApplicationComponent
import com.serhiiromanchuk.utilitybills.presentation.screen.choose_bill.components.AddNewBill
import com.serhiiromanchuk.utilitybills.presentation.screen.choose_bill.components.BillAddressCard
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme

@Composable
fun ChooseBillScreen(
    modifier: Modifier = Modifier,
    onAddBillClick: () -> Unit,
    onBillItemClick: (Long) -> Unit
) {
    val component = getApplicationComponent()
    val viewModel: ChooseBillViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsState()

    Scaffold(
        topBar = {
            TopBarApp(titleId = R.string.utility_bills)
        }
    ) {
        ChooseBillScreenContent(
            modifier = modifier.padding(it),
            screenState = screenState.value,
            onAddBillClick = onAddBillClick,
            onBillItemClick = onBillItemClick
        )
    }
}

@Composable
private fun ChooseBillScreenContent(
    modifier: Modifier = Modifier,
    screenState: ChooseBillState,
    onAddBillClick: () -> Unit,
    onBillItemClick: (Long) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Adaptive(minSize = 140.dp),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_large)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_extra_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_extra_small)),
    ) {
        items(screenState.billList, key = { it.id }) { billItem ->
            BillAddressCard(
                billAddress = billItem.address,
                onLongClick = {  },
                onClick = { onBillItemClick(billItem.id) }
            )
        }
        item {
            AddNewBill( onAddBillClick = onAddBillClick )
        }
    }
}

@DarkLightPreviews
@Composable
private fun ChooseBillScreenLayoutPreview() {
    UtilityBillsTheme() {
        ChooseBillScreenContent(
            screenState = ChooseBillState(
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
            onBillItemClick = {}
        )
    }
}