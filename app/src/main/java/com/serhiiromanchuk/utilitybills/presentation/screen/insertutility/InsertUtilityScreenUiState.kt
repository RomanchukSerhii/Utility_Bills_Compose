package com.serhiiromanchuk.utilitybills.presentation.screen.insertutility

import com.serhiiromanchuk.utilitybills.domain.model.MeasurementUnit

sealed class InsertUtilityServiceScreenUiState {
    data class Initial(
        val name: String = "",
        val tariff: String = "",
        val isMeterAvailable: Boolean = true,
        val previousValue: String = "0",
        val unitOfMeasurement: MeasurementUnit = MeasurementUnit.CUBIC_METER
    ) : InsertUtilityServiceScreenUiState()

    data class Content (
        val name: String,
        val tariff: String,
        val isMeterAvailable: Boolean,
        val previousValue: String,
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