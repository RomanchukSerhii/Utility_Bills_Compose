package com.serhiiromanchuk.utilitybills.presentation.viewmodel

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.GetBillItemsUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.InsertBillItemUseCase
import com.serhiiromanchuk.utilitybills.presentation.screen.StartScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class StartScreenViewModel @Inject constructor(
    getBillItemsUseCase: GetBillItemsUseCase,
    private val insertBillItemUseCase: InsertBillItemUseCase
) : ViewModel() {

    private val _screenUiState = MutableStateFlow<StartScreenUiState>(StartScreenUiState.Initial())
    val screenUiState: StateFlow<StartScreenUiState> = _screenUiState.asStateFlow()

    fun insertBillItem(address: String, cardNumber: String) {
        val isAddressEmpty = address.isEmpty()
        val isCardNumberEmpty = cardNumber.isEmpty()
        val isCardNumberNotValid = cardNumber.length == 19

        if (isAddressEmpty || isCardNumberEmpty || isCardNumberNotValid) {
            _screenUiState.value = StartScreenUiState.Error(
                isAddressFieldEmpty = isAddressEmpty,
                isCardNumberFieldEmpty = isCardNumberEmpty,
                isCardNumberNotValid = isCardNumberNotValid
            )
        } else {
            val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDate.now()
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val year = date.year
            val month = date.month
            val billItem = BillItem(
                address = address,
                month = month,
                year = year,
                cardNumber = cardNumber
            )

            viewModelScope.launch {
                insertBillItemUseCase(billItem)
            }
        }
    }

    init {
        getBillItemsUseCase().onEach {
            if (it.isNotEmpty()) _screenUiState.value = StartScreenUiState.LoadingMainScreen
        }.launchIn(viewModelScope)
    }
}