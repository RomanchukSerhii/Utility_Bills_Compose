package com.serhiiromanchuk.utilitybills.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.GetBillItemsUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.InsertBillItemUseCase
import com.serhiiromanchuk.utilitybills.presentation.screen.start.StartScreenEvent
import com.serhiiromanchuk.utilitybills.presentation.screen.start.StartScreenUiState
import com.serhiiromanchuk.utilitybills.presentation.screen.start.StartScreenUiState.Error
import com.serhiiromanchuk.utilitybills.presentation.screen.start.StartScreenUiState.Initial
import com.serhiiromanchuk.utilitybills.presentation.screen.start.StartScreenUiState.LoadingMainScreen
import com.serhiiromanchuk.utilitybills.utils.getCurrentMonth
import com.serhiiromanchuk.utilitybills.utils.getCurrentYear
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class StartScreenViewModel @Inject constructor(
    getBillItemsUseCase: GetBillItemsUseCase,
    private val insertBillItemUseCase: InsertBillItemUseCase
) : ViewModel() {

    private val _screenUiState = MutableStateFlow<StartScreenUiState>(Initial)
    val screenUiState: StateFlow<StartScreenUiState> = _screenUiState.asStateFlow()

    fun onEvent(event: StartScreenEvent) {
        when (event) {
            is StartScreenEvent.InsertUtilityBill -> { insertBillItem(event.address, event.cardNumber) }
        }
    }

    private fun insertBillItem(address: String, cardNumber: String){
        val isAddressEmpty = address.isEmpty()
        val isCardNumberEmpty = cardNumber.isEmpty()
        val isCardNumberNotValid = cardNumber.length == 19

        if (isAddressEmpty || isCardNumberEmpty || isCardNumberNotValid) {
            _screenUiState.value = Error(
                isAddressFieldEmpty = isAddressEmpty,
                isCardNumberFieldEmpty = isCardNumberEmpty,
                isCardNumberNotValid = isCardNumberNotValid
            )
        } else {
            val billItem = BillItem(
                address = address,
                month = getCurrentMonth(),
                year = getCurrentYear(),
                cardNumber = cardNumber
            )

            viewModelScope.launch {
                insertBillItemUseCase(billItem)
            }

            _screenUiState.value = LoadingMainScreen
        }
    }

    init {
        getBillItemsUseCase().onEach {
            if (it.isNotEmpty()) _screenUiState.value = LoadingMainScreen
        }.launchIn(viewModelScope)
    }
}