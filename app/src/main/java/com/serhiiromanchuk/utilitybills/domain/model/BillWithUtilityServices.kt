package com.serhiiromanchuk.utilitybills.domain.model

data class BillWithUtilityServices(
    val bill: BillItem,
    val utilityServices: List<UtilityServiceItem>
)
