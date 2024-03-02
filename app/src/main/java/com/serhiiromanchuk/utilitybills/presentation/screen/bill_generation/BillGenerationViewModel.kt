package com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.GetBillWithUtilityServicesUseCase
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation.BillGenerationUiState.ServiceItemState
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

class BillGenerationViewModel @Inject constructor(
    private val billId: Long,
    private val getBillWithUtilityServicesUseCase: GetBillWithUtilityServicesUseCase
) : ViewModel() {

    private val bufferUtilityServicesList = mutableStateListOf<ServiceItemState>()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent.asSharedFlow()

    private val _screenState = MutableStateFlow(BillGenerationUiState())
    val screenState: StateFlow<BillGenerationUiState> = _screenState.asStateFlow()

    init {

        viewModelScope.launch {
            getBillWithUtilityServicesUseCase(billId).collect { currentBill ->
                if (bufferUtilityServicesList.isNotEmpty()) bufferUtilityServicesList.clear()
                currentBill.utilityServices.forEach { utilityService ->
                    bufferUtilityServicesList.add(ServiceItemState(utilityService))
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

    fun onEvent(event: BillGenerationUiEvent) {
        when (event) {
            is BillGenerationUiEvent.AddUtilityService -> {
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationEvent.OnAddService(event.billCreatorId))
                }
            }

            BillGenerationUiEvent.EditBillInfo -> TODO()
            BillGenerationUiEvent.Submit -> TODO()
            BillGenerationUiEvent.OnBackClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationEvent.OnBack)
                }
            }

            is BillGenerationUiEvent.CheckStateChanged -> {
                changeServiceCheckedState(event.serviceId, event.checked)
            }
            is BillGenerationUiEvent.CurrentValueChanged -> {
                meterValueChange(event.serviceId, event.value, MeterValueType.CURRENT)
            }
            is BillGenerationUiEvent.PreviousValueChanged -> {
                meterValueChange(event.serviceId, event.value, MeterValueType.PREVIOUS)
            }

            is BillGenerationUiEvent.OnEditServiceClicked -> TODO()
        }
    }

    private fun meterValueChange(id: Long, value: String, meterValueType: MeterValueType) {
        bufferUtilityServicesList.apply {
            replaceAll { oldServiceState ->
                val oldServiceItem = oldServiceState.utilityServiceItem

                if (oldServiceItem.id == id) {
                    val newServiceItem = when (meterValueType) {
                        MeterValueType.PREVIOUS -> {
                            oldServiceItem.copy(previousValue = value)
                        }
                        MeterValueType.CURRENT -> {
                            oldServiceItem.copy(currentValue = value)
                        }
                    }
                    oldServiceState.copy(utilityServiceItem = newServiceItem)
                } else {
                    oldServiceState
                }
            }
        }
        updateList()
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
        updateList()
    }

    private fun updateList() {
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