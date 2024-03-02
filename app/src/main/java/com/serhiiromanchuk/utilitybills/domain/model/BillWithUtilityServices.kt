package com.serhiiromanchuk.utilitybills.domain.model

data class BillWithUtilityServices(
    val bill: Bill,
    val utilityServices: List<UtilityService>
)
