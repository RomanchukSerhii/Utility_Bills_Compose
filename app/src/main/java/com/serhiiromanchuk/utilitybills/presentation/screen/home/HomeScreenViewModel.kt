package com.serhiiromanchuk.utilitybills.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.mocks.fakeBillItem
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.GetBillWithUtilityServicesUseCase
import com.serhiiromanchuk.utilitybills.utils.MeterValueType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeScreenViewModel @Inject constructor(
    private val getBillWithUtilityServicesUseCase: GetBillWithUtilityServicesUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(HomeScreenState())
    val screenState: StateFlow<HomeScreenState> = _screenState.asStateFlow()

    private val bufferUtilityServicesList = mutableListOf<UtilityServiceItem>()

    init {
        viewModelScope.launch {
            val billWithUtilityServices = getBillWithUtilityServicesUseCase()
            val currentBill = billWithUtilityServices.firstOrNull { it.bill.id == fakeBillItem.id }
            currentBill?.utilityServices?.forEach {
                bufferUtilityServicesList.add(it)
            }

            _screenState.update {
                it.copy(list = bufferUtilityServicesList)
            }
        }
    }

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

data class HomeScreenState(
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