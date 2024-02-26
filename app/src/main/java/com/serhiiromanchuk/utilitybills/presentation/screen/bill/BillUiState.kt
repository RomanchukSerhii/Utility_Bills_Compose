package com.serhiiromanchuk.utilitybills.presentation.screen.bill

import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem

data class BillUiState(
    val bill: BillItem = BillItem(
        payerName = "",
        address = "",
        month = "",
        year = "",
    ),
    val list: List<ServiceItemState> = listOf()
) {
    val isNextButtonEnabled: Boolean
        get() {
            list.forEach { utilityService ->
                if (utilityService.isChecked) return true
            }
            return false
        }

    data class ServiceItemState(
        val utilityServiceItem: UtilityServiceItem,
        val previousValue: String = utilityServiceItem.previousValue,
        val currentValue: String = utilityServiceItem.currentValue,
        val isChecked: Boolean = true,
    )
}
