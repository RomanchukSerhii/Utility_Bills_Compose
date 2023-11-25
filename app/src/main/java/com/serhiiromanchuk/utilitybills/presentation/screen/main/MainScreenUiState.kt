package com.serhiiromanchuk.utilitybills.presentation.screen.main

import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem

sealed class MainScreenUiState {
    data class Initial(
        val address: String,
        val month: String,
        val cardNumber: String,
        val utilityServices: List<UtilityServiceItem>
    )
}