package com.serhiiromanchuk.utilitybills.presentation.screen.start.add_bill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.Bill
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.InsertBillItemUseCase
import com.serhiiromanchuk.utilitybills.utils.getCurrentMonth
import com.serhiiromanchuk.utilitybills.utils.getCurrentYear
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddBillViewModel @Inject constructor(
    private val insertBillItemUseCase: InsertBillItemUseCase,
    private val getMaxItemPositionUseCase: GetMaxItemPositionUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(AddBillScreenState())
    val screenState: StateFlow<AddBillScreenState> = _screenState.asStateFlow()

    fun onEvent(event: AddBillScreenEvent) {
        when (event) {
            is AddBillScreenEvent.PayerNameChanged -> {
                _screenState.update { it.copy(payerName = event.payerName) }
            }
            is AddBillScreenEvent.CityChanged -> {
                _screenState.update { it.copy(city = event.city) }
            }
            is AddBillScreenEvent.StreetChanged -> {
                _screenState.update { it.copy(street = event.street) }
            }
            is AddBillScreenEvent.ApartmentChanged -> {
                _screenState.update { it.copy(apartment = event.apartment) }
            }
            is AddBillScreenEvent.BuildingChanged -> {
                _screenState.update { it.copy(building = event.building) }
            }
            is AddBillScreenEvent.HouseChanged -> {
                _screenState.update { it.copy(house = event.house) }
            }
            is AddBillScreenEvent.Submit -> { submitData(event.address, event.payerName) }
        }
    }

    private fun submitData(address: String, payerName: String) {
        viewModelScope.launch {
            val lastIndex = getMaxItemPositionUseCase()
            insertBillItemUseCase(
                Bill(
                    address = address,
                    payerName = payerName,
                    month = getCurrentMonth(),
                    year = getCurrentYear(),
                    indexPosition = lastIndex?.let { it + 1 } ?: 0,
                )
            )
        }
    }
}