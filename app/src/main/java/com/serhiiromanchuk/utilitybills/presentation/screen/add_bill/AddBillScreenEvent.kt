package com.serhiiromanchuk.utilitybills.presentation.screen.add_bill

sealed class AddBillScreenEvent {
    data class AddressChanged(val address: String) : AddBillScreenEvent()
    data class CardNumberChanged(val cardNumber: String) : AddBillScreenEvent()
    data object Submit : AddBillScreenEvent()
}