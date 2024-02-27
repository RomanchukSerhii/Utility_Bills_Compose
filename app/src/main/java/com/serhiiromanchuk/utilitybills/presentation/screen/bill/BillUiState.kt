package com.serhiiromanchuk.utilitybills.presentation.screen.bill

import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.utils.trimSpaces

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
        val isChecked: Boolean = true,
    ) {
        val currentTextFieldError: Boolean
            get() {
                val previousDigit = utilityServiceItem.previousValue.trimSpaces().toIntOrNull() ?: 0
                val currentDigit = utilityServiceItem.currentValue.trimSpaces().toIntOrNull() ?: 0
                return previousDigit > currentDigit
            }
    }
}
