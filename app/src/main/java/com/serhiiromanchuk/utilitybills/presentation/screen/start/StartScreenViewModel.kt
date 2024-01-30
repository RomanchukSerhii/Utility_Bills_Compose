package com.serhiiromanchuk.utilitybills.presentation.screen.start

import androidx.lifecycle.ViewModel
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.GetBillWithUtilityServicesUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.InsertBillItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class StartScreenViewModel @Inject constructor(
    private val getBillWithUtilityServicesUseCase: GetBillWithUtilityServicesUseCase,
    private val insertBillItemUseCase: InsertBillItemUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(StartScreenUiState())
    val screenState: StateFlow<StartScreenUiState> = _screenState.asStateFlow()

    fun onEvent(event: StartScreenEvent) {
        when (event) {
            is StartScreenEvent.AddressChanged -> {
                _screenState.update {
                    it.copy(
                        address = event.address
                    )
                }
            }
            is StartScreenEvent.CardNumberChanged -> {
                _screenState.update {
                    it.copy(
                        cardNumber = event.cardNumber
                    )
                }
            }
            StartScreenEvent.Submit -> TODO()
        }
    }


}