package com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation

import com.serhiiromanchuk.utilitybills.domain.model.Bill
import com.serhiiromanchuk.utilitybills.domain.model.UtilityService
import com.serhiiromanchuk.utilitybills.utils.UNDEFINED_ID
import com.serhiiromanchuk.utilitybills.utils.trimSpaces

data class BillGenerationUiState(
    val bill: Bill = Bill(
        packageCreatorId = UNDEFINED_ID,
        payerName = "",
        address = "",
        date = ""
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
        val utilityServiceItem: UtilityService,
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
