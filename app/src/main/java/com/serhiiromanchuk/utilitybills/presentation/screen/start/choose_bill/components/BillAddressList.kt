package com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.presentation.core.components.grid.LazyDraggableVerticalGrid
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

    LazyDraggableVerticalGrid(
        modifier = modifier.fillMaxSize(),
        items = screenState.billList,
        key = { _, item -> item.id },
        columns = GridCells.Adaptive(minSize = 140.dp),
        onMove = { fromIndex, toIndex -> onEvent(ChooseBillEvent.MoveBill(fromIndex, toIndex)) },
        notDraggableContent = { AddNewBill(onAddBillClick = onAddBillClick) },
        draggableContent = { billItem, isDragging ->
            val elevation by animateDpAsState(if (isDragging) 4.dp else 1.dp, label = "elevation")
            BillAddressCard(
                billAddress = billItem.address,
                cardState = screenState.billCardState,
                elevation = elevation,
                onLongClick = {
                    onEvent(ChooseBillEvent.OpenBottomSheet(billItem.address, billItem.id))
                },
                onClick = { onBillItemClick(billItem.id) },
                onDeleteIconClick = { onEvent(ChooseBillEvent.OpenDialog(billItem.id)) }
            )
        }
    )


//    LazyVerticalGrid(
//        modifier = modifier.fillMaxSize(),
//        columns = GridCells.Adaptive(minSize = 140.dp),
//        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_medium)),
//    ) {
//        items(screenState.billList, key = { it.id }) { billItem ->
//            BillAddressCard(
//                billAddress = billItem.address,
//                cardState = screenState.billCardState,
//                onLongClick = {
//                    onEvent(ChooseBillEvent.OpenBottomSheet(billItem.address, billItem.id))
//                },
//                onClick = { onBillItemClick(billItem.id) },
//                onDeleteIconClick = { onEvent(ChooseBillEvent.OpenDialog(billItem.id)) }
//            )
//        }
//        item {
//            AddNewBill(onAddBillClick = onAddBillClick)
//        }
//    }
}