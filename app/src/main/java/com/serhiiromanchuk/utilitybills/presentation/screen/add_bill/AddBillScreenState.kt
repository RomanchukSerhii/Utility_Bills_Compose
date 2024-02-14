package com.serhiiromanchuk.utilitybills.presentation.screen.add_bill

import com.serhiiromanchuk.utilitybills.utils.UiText

data class AddBillScreenState (
    val street: String = "",
    val streetError: UiText? = null,
    val house: String = "",
    val houseError: UiText? = null,
    val building: String = "",
    val apartment: String = "",
    val nextButtonAvailable: Boolean = false
)