package com.serhiiromanchuk.utilitybills.domain.model

data class BillItem(
    val id: Int = UNDEFINED_ID,
    val address: String,
    val month: String,
    val year: String,
    val cardNumber: String,
    val billDescription: String = ""
) {
    companion object {
        private const val UNDEFINED_ID = 0
    }
}