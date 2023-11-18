package com.serhiiromanchuk.utilitybills.presentation.screen

import com.serhiiromanchuk.utilitybills.domain.model.MeasurementUnit
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

sealed class InsertUtilityServiceScreenUiState {

    data class Initial(
        val name: String = "",
        val tariff: String = "",
        val isMeterAvailable: Boolean = true,
        val previousValue: Int = 0,
        val unitOfMeasurement: MeasurementUnit = MeasurementUnit.CUBIC_METER
    ) : InsertUtilityServiceScreenUiState()

    data class Content (
        val name: String,
        val tariff: String,
        val isMeterAvailable: Boolean,
        val previousValue: Int,
        val unitOfMeasurement: MeasurementUnit
    ) : InsertUtilityServiceScreenUiState()

    data class Error (
        val isNameEmpty: Boolean,
        val isTariffEmpty: Boolean,
        val isTariffNotDouble: Boolean,
        val isPreviousValueEmpty: Boolean,
        val isPreviousValueNotDigit: Boolean
    ) : InsertUtilityServiceScreenUiState()
}
