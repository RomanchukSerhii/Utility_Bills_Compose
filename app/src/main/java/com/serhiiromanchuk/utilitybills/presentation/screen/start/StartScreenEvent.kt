package com.serhiiromanchuk.utilitybills.presentation.screen.start

sealed class StartScreenEvent {
    data class InsertUtilityBill(
        val address: String,
        val cardNumber: String,
    ) : StartScreenEvent()
}