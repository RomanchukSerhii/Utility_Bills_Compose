package com.serhiiromanchuk.utilitybills.presentation.screen.start

import com.serhiiromanchuk.utilitybills.utils.UiText

data class StartScreenUiState (
    val address: String = "",
    val addressError: UiText? = null,
    val cardNumber: String = "",
    val cardNumberError: UiText? = null
)


