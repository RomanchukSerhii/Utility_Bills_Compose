package com.serhiiromanchuk.utilitybills.domain.model

data class UtilityServiceItem(
    val id: Int = UNDEFINED_ID,
    val address: String,
    val name: String,
    val tariff: Double,
    val isMeterAvailable: Boolean,
    val previousValue: Int,
    val currentValue: Int = UNDEFINED_CURRENT_VALUE
) {
    companion object {
        private const val UNDEFINED_ID = 0
        private const val UNDEFINED_CURRENT_VALUE = 0
    }
}
