package com.serhiiromanchuk.utilitybills.domain.model

data class BillPackage(
    val id: Long = UNDEFINED_ID,
    val name: String,
    val indexPosition: Int = INITIAL_INDEX_POSITION
) {
    companion object {
        private const val UNDEFINED_ID = 0L
        private const val INITIAL_INDEX_POSITION = 0
    }
}
