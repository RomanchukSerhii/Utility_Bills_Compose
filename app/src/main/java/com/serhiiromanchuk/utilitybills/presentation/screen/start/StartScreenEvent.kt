package com.serhiiromanchuk.utilitybills.presentation.screen.start

sealed class StartScreenEvent {
    data class AddressChanged(val address: String) : StartScreenEvent()
    data class CardNumberChanged(val cardNumber: String) : StartScreenEvent()
    data object Submit : StartScreenEvent()
}