package com.serhiiromanchuk.utilitybills.presentation.screen.add_bill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.usecase.add_bill_screen.ValidateAddressUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.add_bill_screen.ValidateCardNumberUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.InsertBillItemUseCase
import com.serhiiromanchuk.utilitybills.utils.getCurrentMonth
import com.serhiiromanchuk.utilitybills.utils.getCurrentYear
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddBillViewModel @Inject constructor(
    private val insertBillItemUseCase: InsertBillItemUseCase,
    private val validateAddressUseCase: ValidateAddressUseCase,
    private val validateCardNumberUseCase: ValidateCardNumberUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(AddBillScreenState())
    val screenState: StateFlow<AddBillScreenState> = _screenState.asStateFlow()

    private val _validationEvents = MutableSharedFlow<ValidationEvents>()
    val validationEvents: SharedFlow<ValidationEvents> = _validationEvents.asSharedFlow()

    fun onEvent(event: AddBillScreenEvent) {
        when (event) {
            is AddBillScreenEvent.AddressChanged -> {
                _screenState.update {
                    it.copy(
                        address = event.address,
                        addressError = null
                    )
                }
            }

            is AddBillScreenEvent.CardNumberChanged -> {
                _screenState.update {
                    it.copy(
                        cardNumber = event.cardNumber,
                        cardNumberError = null
                    )
                }
            }

            AddBillScreenEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val validateAddressResult = validateAddressUseCase(_screenState.value.address)
        val validateCardNumberResult = validateCardNumberUseCase(_screenState.value.cardNumber)

        val hasError = listOf(
            validateAddressResult,
            validateCardNumberResult
        ).any { !it.successful }

        if (hasError) {
            _screenState.update {
                it.copy(
                    addressError = validateAddressResult.errorMessage,
                    cardNumberError = validateCardNumberResult.errorMessage
                )
            }
            return
        }

        viewModelScope.launch {
            insertBillItemUseCase(
                BillItem(
                    address = screenState.value.address,
                    month = getCurrentMonth(),
                    year = getCurrentYear(),
                    cardNumber = screenState.value.cardNumber
                )
            )
            _validationEvents.emit(ValidationEvents.Success)
        }
    }

    sealed class ValidationEvents {
        data object Success : ValidationEvents()
    }
}