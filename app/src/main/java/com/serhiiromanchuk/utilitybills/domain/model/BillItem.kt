package com.serhiiromanchuk.utilitybills.domain.model

data class BillItem(
    val id: Long = UNDEFINED_ID,
    val address: String,
    val month: String,
    val year: String,
    val indexPosition: Int,
    val cardNumber: String = "0000 0000 0000 0000",
    val billDescription: String = ""
) {
    companion object {
        private const val UNDEFINED_ID = 0L
    }
}