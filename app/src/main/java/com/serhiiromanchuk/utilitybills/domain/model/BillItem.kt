package com.serhiiromanchuk.utilitybills.domain.model

data class BillItem(
    val id: Int = UNDEFINED_ID,
    val address: String,
    val month: String,
    val year: Int,
    val cardNumber: String,
    val utilityServices: List<UtilityServiceItem>,
    val billDescription: String = ""
) {
    companion object {
        private const val UNDEFINED_ID = 0
    }
}