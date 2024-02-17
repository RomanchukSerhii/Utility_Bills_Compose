package com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill

import com.serhiiromanchuk.utilitybills.domain.model.BillItem

data class ChooseBillState (
    val billList: List<BillItem> = listOf(),
    val isEditMode: Boolean = false,
    val dialogState: DialogState = DialogState.Close,
    val visibleSheetState: VisibleSheetState = VisibleSheetState.Close
) {
    sealed class DialogState(val billId: Long? = null) {
        data class Open(val id: Long) : DialogState(id)
        data object Close : DialogState()
    }

    sealed class VisibleSheetState(val address: String? = null) {
        data class Open(val billAddress: String) : VisibleSheetState(billAddress)
        data object Close : VisibleSheetState()
    }
}


