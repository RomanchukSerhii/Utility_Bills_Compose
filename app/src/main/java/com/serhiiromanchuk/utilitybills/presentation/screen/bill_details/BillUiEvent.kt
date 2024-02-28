package com.serhiiromanchuk.utilitybills.presentation.screen.bill_details

sealed interface BillUiEvent {
    data object OnBackClicked : BillUiEvent

    data object OnCopyClicked : BillUiEvent

    data object OnDetailsClicked : BillUiEvent
}