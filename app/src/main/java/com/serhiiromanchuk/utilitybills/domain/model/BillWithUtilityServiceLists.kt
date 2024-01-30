package com.serhiiromanchuk.utilitybills.domain.model

data class BillWithUtilityServiceLists(
    val bill: BillItem,
    val utilityServices: List<UtilityServiceItem>
)
