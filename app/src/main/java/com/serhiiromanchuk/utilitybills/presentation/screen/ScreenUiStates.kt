package com.serhiiromanchuk.utilitybills.presentation.screen

import com.serhiiromanchuk.utilitybills.domain.model.MeasurementUnit
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem

sealed class StartScreenUiState {
    data class Initial(
        val address: String = "",
        val cardNumber: String = ""
    ) : StartScreenUiState()

    data object LoadingMainScreen : StartScreenUiState()

    data class Error(
        val isAddressFieldEmpty: Boolean,
        val isCardNumberFieldEmpty: Boolean,
        val isCardNumberNotValid: Boolean
    ) : StartScreenUiState()
}

sealed class MainScreenUiState {
    data class Initial(
        val address: String,
        val month: String,
        val cardNumber: String,
        val utilityServices: List<UtilityServiceItem>
    )
}

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
