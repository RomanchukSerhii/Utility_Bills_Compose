package com.serhiiromanchuk.utilitybills.presentation.screen.insertservice

import com.serhiiromanchuk.utilitybills.domain.model.MeasurementUnit
import com.serhiiromanchuk.utilitybills.utils.UiText

data class InsertServiceFormState(
    val name: String = "",
    val nameError: UiText? = null,
    val tariff: String = "0",
    val tariffError: UiText? = null,
    val isMeterAvailable: Boolean = true,
    val previousValue: String = "",
    val previousValueError: UiText? = null,
    val unitOfMeasurement: MeasurementUnit = MeasurementUnit.CUBIC_METER,
)
