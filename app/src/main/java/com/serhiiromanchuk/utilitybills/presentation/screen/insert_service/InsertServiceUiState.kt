package com.serhiiromanchuk.utilitybills.presentation.screen.insert_service

import com.serhiiromanchuk.utilitybills.domain.model.MeasurementUnit

data class InsertServiceUiState(
    val serviceName: String = "",
    val tariff: String = "0",
    val isMeterAvailable: Boolean = true,
    val previousValue: String = "",
    val currentValue: String = "",
    val unitOfMeasurement: MeasurementUnit = MeasurementUnit.CUBIC_METER,
) {
    val isSaveButtonAvailable: Boolean = if (isMeterAvailable) {
        serviceName.isNotEmpty() && tariff.isNotEmpty() && previousValue.isNotEmpty()
    } else {
        serviceName.isNotEmpty() && tariff.isNotEmpty()
    }
}
