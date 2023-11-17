package com.serhiiromanchuk.utilitybills.presentation.screenstate

import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem

data class MainScreenUiState (
    val id: Int = UNDEFINED_ID,
    val address: String,
    val month: String,
    val cardNumber: String,
    val utilityServices: List<UtilityServiceItem>

) {
    companion object {
        private const val UNDEFINED_ID = 0
    }
}