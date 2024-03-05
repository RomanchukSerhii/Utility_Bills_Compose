package com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation

import com.serhiiromanchuk.utilitybills.domain.model.UtilityService

sealed interface BillGenerationUiEvent {
    data object EditBillInfo : BillGenerationUiEvent

    data class AddUtilityService(val billCreatorId: Long) : BillGenerationUiEvent

    data class DeleteUtilityService(val serviceId: Long) : BillGenerationUiEvent

    data object OnBackClicked : BillGenerationUiEvent

    data class PreviousValueChanged(val serviceId: Long, val value: String) : BillGenerationUiEvent

    data class CurrentValueChanged(val serviceId: Long, val value: String) : BillGenerationUiEvent

    data class CheckStateChanged(val serviceId: Long, val checked: Boolean) : BillGenerationUiEvent

    data class OnEditServiceClicked(val serviceId: Long) : BillGenerationUiEvent

    data class OpenDialog(val service: UtilityService) : BillGenerationUiEvent

    data object CloseDialog : BillGenerationUiEvent

    data object Submit : BillGenerationUiEvent
}