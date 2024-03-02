package com.serhiiromanchuk.utilitybills.presentation.screen.bill_details

import com.serhiiromanchuk.utilitybills.domain.model.UtilityService

data class BillUiState(
    val billDescription: String = "",
    val services: List<UtilityService> = listOf()
)