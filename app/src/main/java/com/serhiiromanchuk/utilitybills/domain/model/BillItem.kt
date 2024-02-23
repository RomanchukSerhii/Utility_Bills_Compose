package com.serhiiromanchuk.utilitybills.domain.model

data class BillItem(
    val id: Long = UNDEFINED_ID,
    val payerName: String,
    val address: String,
    val month: String,
    val year: String,
    val indexPosition: Int = INITIAL_INDEX_POSITION,
    val billDescription: String = ""
) {
    companion object {
        private const val UNDEFINED_ID = 0L
        private const val INITIAL_INDEX_POSITION = 0
    }
}