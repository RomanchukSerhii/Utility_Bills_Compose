package com.serhiiromanchuk.utilitybills.domain.model

data class UtilityService(
    val id: Long = UNDEFINED_ID,
    val billCreatorId: Long,
    val name: String,
    val tariff: Double,
    val isMeterAvailable: Boolean,
    val previousValue: String = UNDEFINED_CURRENT_VALUE,
    val currentValue: String = UNDEFINED_CURRENT_VALUE,
    val unitOfMeasurement: MeasurementUnit = MeasurementUnit.CUBIC_METER
) {
    companion object {
        private const val UNDEFINED_ID = 0L
        private const val UNDEFINED_CURRENT_VALUE = "0"
    }
}

enum class MeasurementUnit(val title: String) {
    CUBIC_METER("куб."),
    KILOWATT("кВт.")
}
