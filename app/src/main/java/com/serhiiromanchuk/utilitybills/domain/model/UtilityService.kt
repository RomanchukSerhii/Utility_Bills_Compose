package com.serhiiromanchuk.utilitybills.domain.model

import com.serhiiromanchuk.utilitybills.utils.INITIAL_INDEX_POSITION
import com.serhiiromanchuk.utilitybills.utils.UNDEFINED_ID
import com.serhiiromanchuk.utilitybills.utils.UNDEFINED_METER_VALUE
import com.serhiiromanchuk.utilitybills.utils.trimSpaces
import kotlin.math.ceil

data class UtilityService(
    val id: Long = UNDEFINED_ID,
    val billCreatorId: Long,
    val name: String,
    val tariff: Double,
    val isMeterAvailable: Boolean,
    val previousValue: String = UNDEFINED_METER_VALUE,
    val currentValue: String = UNDEFINED_METER_VALUE,
    val unitOfMeasurement: MeasurementUnit = MeasurementUnit.CUBIC_METER,
    val indexPosition: Int = INITIAL_INDEX_POSITION
) {

    private val previousDigit = previousValue.trimSpaces().toIntOrNull() ?: 0
    private val currentDigit = currentValue.trimSpaces().toIntOrNull() ?: 0
    private val consumedValue = currentDigit - previousDigit

    val consumed = "$consumedValue ${unitOfMeasurement.title}"
    val total = if (isMeterAvailable) {
        ceil(consumedValue * tariff)
    } else {
        tariff
    }
}

enum class MeasurementUnit(val title: String) {
    CUBIC_METER("куб."),
    KILOWATT("кВт.")
}
