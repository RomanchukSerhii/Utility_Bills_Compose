package com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation

import com.serhiiromanchuk.utilitybills.domain.model.Bill
import com.serhiiromanchuk.utilitybills.domain.model.UtilityService
import com.serhiiromanchuk.utilitybills.utils.NOT_FOUND_ID
import com.serhiiromanchuk.utilitybills.utils.getCurrentDate
import com.serhiiromanchuk.utilitybills.utils.trimSpaces

data class BillGenerationUiState(
    val bill: Bill = Bill(
        packageCreatorId = NOT_FOUND_ID,
        date = ""
    ),
    val packageState: PackageState = PackageState(),
    val serviceStateList: List<ServiceItemState> = listOf(),
    val date: String = getCurrentDate(),
    val dialogState: DialogState = DialogState.Close
) {
    val isNextButtonEnabled: Boolean
        get() {
            serviceStateList.forEach { utilityService ->
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

    data class PackageState(
        val billList: List<Bill> = listOf(),
        val payerName: String = "",
        val address: String = "",
    )

    sealed interface DialogState {
        data class Open(val service: UtilityService) : DialogState
        data object Close : DialogState
    }
}
