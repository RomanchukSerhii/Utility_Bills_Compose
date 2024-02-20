package com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill

import com.serhiiromanchuk.utilitybills.domain.model.BillItem

data class ChooseBillState (
    val billList: List<BillItem> = listOf(),
    val isEditMode: Boolean = false,
    val billCardState: BillCardState = BillCardState.Initial,
    val dialogState: DialogState = DialogState.Close,
    val visibleSheetState: VisibleSheetState = VisibleSheetState.Close
) {
    sealed interface BillCardState {
        data object Initial : BillCardState

        data object EditMode : BillCardState
    }


    sealed interface DialogState {
        data class Open(val id: Long) : DialogState
        data object Close : DialogState
    }

    sealed interface VisibleSheetState {
        data class Open(val billAddress: String, val billId: Long) : VisibleSheetState
        data object Close : VisibleSheetState
    }
}

