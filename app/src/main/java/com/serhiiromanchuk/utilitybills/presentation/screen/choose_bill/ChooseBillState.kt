package com.serhiiromanchuk.utilitybills.presentation.screen.choose_bill

import com.serhiiromanchuk.utilitybills.domain.model.BillItem

data class ChooseBillState (
    val billList: List<BillItem> = listOf(),
    val isEditMode: Boolean = false,
    val dialogState: DialogState = DialogState.CloseDialog,
    val isSheetOpen: Boolean = false
) {
    sealed class DialogState(val billId: Long? = null) {
        data class OpenDialog(val id: Long) : DialogState(id)
        data object CloseDialog : DialogState()
    }
}


