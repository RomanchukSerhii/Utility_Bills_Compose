package com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill.ChooseBillEvent
import com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill.ChooseBillState

@Composable
fun BillAddressList(
    modifier: Modifier = Modifier,
    screenState: ChooseBillState,
    onEvent: (ChooseBillEvent) -> Unit,
    onAddBillClick: () -> Unit,
    onBillItemClick: (Long) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Adaptive(minSize = 140.dp),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_medium)),
    ) {
        items(screenState.billList, key = { it.id }) { billItem ->
            BillAddressCard(
                bill = billItem,
                cardState = screenState.billCardState,
                onLongClick = { billAddress ->
                    onEvent(ChooseBillEvent.OpenBottomSheet(billAddress))
                },
                onClick = { onBillItemClick(billItem.id) },
                onDeleteIconClick = { onEvent(ChooseBillEvent.OpenDialog(it)) }
            )
        }
        item {
            AddNewBill(onAddBillClick = onAddBillClick)
        }
    }
}