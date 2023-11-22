package com.serhiiromanchuk.utilitybills.domain.model

import java.time.Month
import java.time.Year
import java.time.YearMonth

data class UtilityServiceItem(
    val id: Int = UNDEFINED_ID,
    val address: String,
    val name: String,
    val year: Int,
    val month: Month,
    val tariff: Double,
    val isMeterAvailable: Boolean,
    val previousValue: Int,
    val currentValue: Int = UNDEFINED_CURRENT_VALUE,
    val unitOfMeasurement: MeasurementUnit = MeasurementUnit.CUBIC_METER
) {
    companion object {
        private const val UNDEFINED_ID = 0
        private const val UNDEFINED_CURRENT_VALUE = 0
    }
}

enum class MeasurementUnit(val title: String) {
    CUBIC_METER("куб."),
    KILOWATT("кВт.")
}
