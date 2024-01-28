package com.serhiiromanchuk.utilitybills.presentation.screen.insertservice

import com.serhiiromanchuk.utilitybills.domain.model.MeasurementUnit

data class InsertServiceFormState(
    val name: String = "",
    val nameError: String? = null,
    val tariff: String = "0",
    val tariffError: String? = null,
    val isMeterAvailable: Boolean = false,
    val previousValue: String = "",
    val previousValueError: String? = null,
    val unitOfMeasurement: MeasurementUnit = MeasurementUnit.CUBIC_METER,
)
