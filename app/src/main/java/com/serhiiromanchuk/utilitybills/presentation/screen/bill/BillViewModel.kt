package com.serhiiromanchuk.utilitybills.presentation.screen.bill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.mocks.fakeBillItem
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.GetBillWithUtilityServicesUseCase
import com.serhiiromanchuk.utilitybills.utils.MeterValueType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class BillViewModel @Inject constructor(
    private val billId: Long,
    private val getBillWithUtilityServicesUseCase: GetBillWithUtilityServicesUseCase
) : ViewModel() {

    private val bufferUtilityServicesList = mutableListOf<UtilityServiceItem>()

    val screenState = getBillWithUtilityServicesUseCase()
        .map {billList ->
            val currentBill = billList.firstOrNull { it.bill.id == fakeBillItem.id }
            currentBill?.utilityServices?.forEach {
                bufferUtilityServicesList.add(it)
            }
            BillUiState(list = bufferUtilityServicesList)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = BillUiState()
        )

    fun meterValueChange(id: Long, value: String, meterValueType: MeterValueType) {
        bufferUtilityServicesList.apply {
            replaceAll { oldUtilityService ->
                if (oldUtilityService.id == id) {
                    when (meterValueType) {
                        MeterValueType.PREVIOUS -> oldUtilityService.copy(previousValue = value)
                        MeterValueType.CURRENT -> oldUtilityService.copy(currentValue = value)
                    }
                } else {
                    oldUtilityService
                }
            }
        }
    }

    fun changeServiceCheckedState(id: Long, isChecked: Boolean) {
        bufferUtilityServicesList.apply {
            replaceAll { oldUtilityService ->
                if (oldUtilityService.id == id) {
                    oldUtilityService.copy(isChecked = isChecked)
                } else {
                    oldUtilityService
                }
            }
        }
        bufferUtilityServicesList
    }
}