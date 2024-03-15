package com.serhiiromanchuk.utilitybills.presentation.screen.bill_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.GetBillWithServicesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SharedBillViewModel @Inject constructor(
    private val billId: Long,
    private val getBillWithUtilityServicesUseCase: GetBillWithServicesUseCase
) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent.asSharedFlow()

    private val _screenState = MutableStateFlow(BillUiState())
    val screenState: StateFlow<BillUiState> = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            getBillWithUtilityServicesUseCase(billId).collect { billWithServices ->
//                val services = mutableListOf<UtilityServiceItem>()
//                billWithServices.utilityServices.forEach { service ->
//                    if (service.)
//                }

                _screenState.update {
                    it.copy(
                        billDescription = billWithServices.bill.billDescription ?: "",
                        services = billWithServices.utilityServices
                    )
                }
            }
        }
    }

    fun onEvent(event: BillUiEvent) {
        when (event) {
            BillUiEvent.OnBackClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationEvent.OnBack)
                }
            }
            BillUiEvent.OnCopyClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationEvent.OnCopy)
                }
            }
            BillUiEvent.OnDetailsClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationEvent.OnDetails)
                }
            }
        }
    }

    sealed interface NavigationEvent {
        data object OnBack : NavigationEvent
        data object OnDetails : NavigationEvent
        data object OnCopy : NavigationEvent
    }
}