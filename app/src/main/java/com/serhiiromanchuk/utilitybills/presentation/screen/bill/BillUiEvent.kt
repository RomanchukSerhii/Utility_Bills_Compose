package com.serhiiromanchuk.utilitybills.presentation.screen.bill

sealed interface BillUiEvent {
    data object EditBillInfo : BillUiEvent

    data class AddUtilityService(
        val billCreatorId: Long
    ) : BillUiEvent

    data object OnBackClicked : BillUiEvent

    data object Submit : BillUiEvent
}