package com.serhiiromanchuk.utilitybills.presentation.screen.bill

sealed interface BillUiEvent {
    data object EditBillInfo : BillUiEvent
}