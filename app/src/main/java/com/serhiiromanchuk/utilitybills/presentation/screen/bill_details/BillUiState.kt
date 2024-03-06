package com.serhiiromanchuk.utilitybills.presentation.screen.bill_details

import com.serhiiromanchuk.utilitybills.domain.model.UtilityService

data class BillUiState(
    val billDescription: String = "",
    val totalBillSum: String = "0.00",
    val services: List<UtilityService> = listOf()
)