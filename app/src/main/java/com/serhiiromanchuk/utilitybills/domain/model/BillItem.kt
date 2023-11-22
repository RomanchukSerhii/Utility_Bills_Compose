package com.serhiiromanchuk.utilitybills.domain.model

import java.time.Month
import java.time.Year

data class BillItem(
    val id: Int = UNDEFINED_ID,
    val address: String,
    val month: Month,
    val year: Int,
    val cardNumber: String,
    val billDescription: String = ""
) {
    companion object {
        private const val UNDEFINED_ID = 0
    }
}