package com.serhiiromanchuk.utilitybills.presentation.screen.bill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.GetBillWithUtilityServicesUseCase
import com.serhiiromanchuk.utilitybills.utils.MeterValueType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class BillViewModel @Inject constructor(
    private val billId: Long,
    private val getBillWithUtilityServicesUseCase: GetBillWithUtilityServicesUseCase
) : ViewModel() {

    private val bufferUtilityServicesList = mutableListOf<UtilityServiceItem>()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent.asSharedFlow()

    val screenState = getBillWithUtilityServicesUseCase(billId)
        .map { currentBill ->
            currentBill.utilityServices.forEach {
                bufferUtilityServicesList.add(it)
            }
            BillUiState(
                bill = currentBill.bill,
                list = bufferUtilityServicesList)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = BillUiState()
        )

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