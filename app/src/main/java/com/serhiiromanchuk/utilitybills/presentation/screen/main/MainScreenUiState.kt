package com.serhiiromanchuk.utilitybills.presentation.screen.main

import com.serhiiromanchuk.utilitybills.utils.UiText

sealed class MainScreenUiState {
    data object Initial : MainScreenUiState()

    data class CreateNewBill(
        val address: String = "",
        val addressError: UiText? = null,
        val cardNumber: String = "",
        val cardNumberError: UiText? = null
    ) : MainScreenUiState()

    data object Content : MainScreenUiState()
}