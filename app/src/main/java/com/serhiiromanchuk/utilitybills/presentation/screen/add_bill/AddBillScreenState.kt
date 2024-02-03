package com.serhiiromanchuk.utilitybills.presentation.screen.add_bill

import com.serhiiromanchuk.utilitybills.utils.UiText

data class AddBillScreenState (
    val address: String = "",
    val addressError: UiText? = null,
    val cardNumber: String = "",
    val cardNumberError: UiText? = null
)