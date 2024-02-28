package com.serhiiromanchuk.utilitybills.domain.model

data class Bill(
    val id: Long = UNDEFINED_ID,
    val packageCreatorId: Long,
    val payerName: String,
    val address: String,
    val date: String,
    val billDescription: String = "",
) {
    companion object {
        private const val UNDEFINED_ID = 0L

    }
}