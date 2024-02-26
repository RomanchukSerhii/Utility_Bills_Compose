package com.serhiiromanchuk.utilitybills.presentation.screen.bill

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.GetBillWithUtilityServicesUseCase
import com.serhiiromanchuk.utilitybills.presentation.screen.bill.BillUiState.ServiceItemState
import com.serhiiromanchuk.utilitybills.utils.MeterValueType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class BillViewModel @Inject constructor(
    private val billId: Long,
    private val getBillWithUtilityServicesUseCase: GetBillWithUtilityServicesUseCase
) : ViewModel() {

    private val bufferUtilityServicesList = mutableStateListOf<ServiceItemState>()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent.asSharedFlow()

    private val _screenState = MutableStateFlow(BillUiState())
    val screenState: StateFlow<BillUiState> = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            getBillWithUtilityServicesUseCase(billId).collect { currentBill ->
                currentBill.utilityServices.forEach {
                    bufferUtilityServicesList.add(ServiceItemState(it))
                }
                _screenState.update {
                    it.copy(
                        bill = currentBill.bill,
                        list = bufferUtilityServicesList
                    )
                }
            }
        }
    }

    fun onEvent(event: BillUiEvent) {
        when (event) {
            is BillUiEvent.AddUtilityService -> {
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationEvent.OnAddService(event.billCreatorId))
                }
            }

            BillUiEvent.EditBillInfo -> TODO()
            BillUiEvent.Submit -> TODO()
            BillUiEvent.OnBackClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationEvent.OnBack)
                }
            }

            is BillUiEvent.CheckStateChanged -> {
                changeServiceCheckedState(event.serviceId, event.checked)
            }
            is BillUiEvent.CurrentValueChanged -> {
                meterValueChange(event.serviceId, event.value, MeterValueType.CURRENT)
            }
            is BillUiEvent.PreviousValueChanged -> {
                meterValueChange(event.serviceId, event.value, MeterValueType.PREVIOUS)
            }

            is BillUiEvent.OnEditServiceClicked -> TODO()
        }
    }

    private fun meterValueChange(id: Long, value: String, meterValueType: MeterValueType) {
        bufferUtilityServicesList.apply {
            replaceAll { oldServiceState ->
                if (oldServiceState.utilityServiceItem.id == id) {
                    when (meterValueType) {
                        MeterValueType.PREVIOUS -> oldServiceState.copy(previousValue = value)
                        MeterValueType.CURRENT -> oldServiceState.copy(currentValue = value)
                    }
                } else {
                    oldServiceState
                }
            }
        }
    }

    private fun changeServiceCheckedState(id: Long, isChecked: Boolean) {
        bufferUtilityServicesList.apply {
            replaceAll { oldServiceState ->
                if (oldServiceState.utilityServiceItem.id == id) {
                    oldServiceState.copy(isChecked = isChecked)
                } else {
                    oldServiceState
                }
            }
        }
        _screenState.update {
            it.copy(list = bufferUtilityServicesList)
        }
    }

    sealed interface NavigationEvent {
        data object OnBack : NavigationEvent

        data class OnAddService(
            val billCreatorId: Long
        ) : NavigationEvent

        data class OnEditService(
            val serviceId: Long,
            val billCreatorId: Long
        ) : NavigationEvent

        data object OnCreateBill : NavigationEvent
    }
}