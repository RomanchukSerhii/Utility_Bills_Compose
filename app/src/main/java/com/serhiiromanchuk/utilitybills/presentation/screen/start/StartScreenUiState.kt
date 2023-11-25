package com.serhiiromanchuk.utilitybills.presentation.screen.start

sealed class StartScreenUiState {
    data object Initial : StartScreenUiState()

    data object LoadingMainScreen : StartScreenUiState()

    data class Error(
        val isAddressFieldEmpty: Boolean,
        val isCardNumberFieldEmpty: Boolean,
        val isCardNumberNotValid: Boolean
    ) : StartScreenUiState()
}