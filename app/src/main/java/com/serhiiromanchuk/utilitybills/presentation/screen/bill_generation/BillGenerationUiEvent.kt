package com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation

sealed interface BillGenerationUiEvent {
    data object EditBillInfo : BillGenerationUiEvent

    data class AddUtilityService(val billCreatorId: Long) : BillGenerationUiEvent

    data class DeleteUtilityService(val billCreatorId: Long, val serviceId: Long) : BillGenerationUiEvent

    data object OnBackClicked : BillGenerationUiEvent

    data class PreviousValueChanged(val serviceId: Long, val value: String) : BillGenerationUiEvent

    data class CurrentValueChanged(val serviceId: Long, val value: String) : BillGenerationUiEvent

    data class CheckStateChanged(val serviceId: Long, val checked: Boolean) : BillGenerationUiEvent

    data class OnEditServiceClicked(val serviceId: Long) : BillGenerationUiEvent

    data object Submit : BillGenerationUiEvent
}