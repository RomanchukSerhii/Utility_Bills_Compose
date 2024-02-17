package com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill

sealed interface ChooseBillEvent {
    data object ChangeEditMode : ChooseBillEvent

    data class OpenBottomSheet(val billAddress: String) : ChooseBillEvent

    data object CloseBottomSheet : ChooseBillEvent

    data class DeleteBill(val id: Long) : ChooseBillEvent

    data class OpenDialog(val id: Long) : ChooseBillEvent

    data object CloseDialog : ChooseBillEvent
}