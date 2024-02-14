package com.serhiiromanchuk.utilitybills.presentation.screen.add_bill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.usecase.add_bill_screen.ValidateHouseUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.add_bill_screen.ValidateStreetUseCase
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
    private val validateStreetUseCase: ValidateStreetUseCase,
    private val validateHouseUseCase: ValidateHouseUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(AddBillScreenState())
    val screenState: StateFlow<AddBillScreenState> = _screenState.asStateFlow()

    private val _validationEvents = MutableSharedFlow<ValidationEvents>()
    val validationEvents: SharedFlow<ValidationEvents> = _validationEvents.asSharedFlow()

    fun onEvent(event: AddBillScreenEvent) {
        when (event) {
            is AddBillScreenEvent.ApartmentChanged -> {
                _screenState.update {
                    it.copy(
                        apartment = event.apartment
                    )
                }
            }
            is AddBillScreenEvent.BuildingChanged -> {
                _screenState.update {
                    it.copy(
                        building = event.building
                    )
                }
            }
            is AddBillScreenEvent.HouseChanged -> {
                _screenState.update {
                    it.copy(
                        house = event.house,
                        houseError = null
                    )
                }
            }
            is AddBillScreenEvent.StreetChanged -> {
                _screenState.update {
                    it.copy(
                        street = event.street,
                        streetError = null
                    )
                }
            }

            is AddBillScreenEvent.Submit -> {
                submitData(event.address)
            }
        }
    }

    private fun submitData(address: String) {
        val validateStreetResult = validateStreetUseCase(_screenState.value.street)
        val validateHouseResult = validateHouseUseCase(_screenState.value.house)

        val hasError = listOf(
            validateStreetResult,
            validateHouseResult
        ).any { !it.successful }

        if (hasError) {
            _screenState.update {
                it.copy(
                    streetError = validateStreetResult.errorMessage,
                    houseError = validateHouseResult.errorMessage
                )
            }
            return
        }

        viewModelScope.launch {
            insertBillItemUseCase(
                BillItem(
                    address = address,
                    month = getCurrentMonth(),
                    year = getCurrentYear(),
                    cardNumber = ""
                )
            )
            _validationEvents.emit(ValidationEvents.Success)
        }
    }

    sealed class ValidationEvents {
        data object Success : ValidationEvents()
    }
}