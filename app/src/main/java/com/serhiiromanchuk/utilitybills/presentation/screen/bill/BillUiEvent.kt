package com.serhiiromanchuk.utilitybills.presentation.screen.bill

sealed interface BillUiEvent {
    data object EditBillInfo : BillUiEvent

    data class AddUtilityService(val billCreatorId: Long) : BillUiEvent

    data object OnBackClicked : BillUiEvent

    data class PreviousValueChanged(val serviceId: Long, val value: String) : BillUiEvent

    data class CurrentValueChanged(val serviceId: Long, val value: String) : BillUiEvent

    data class CheckStateChanged(val serviceId: Long, val checked: Boolean) : BillUiEvent

    data class OnEditServiceClicked(val serviceId: Long) : BillUiEvent

    data object Submit : BillUiEvent
}