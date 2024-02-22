package com.serhiiromanchuk.utilitybills.presentation.screen.bill

import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem

data class BillUiState(
    val list: List<UtilityServiceItem> = listOf()
) {
    val isCreateBillEnabled: Boolean
        get() {
            list.forEach { utilityService ->
                if (utilityService.isChecked) return true
            }
            return false
        }
}
