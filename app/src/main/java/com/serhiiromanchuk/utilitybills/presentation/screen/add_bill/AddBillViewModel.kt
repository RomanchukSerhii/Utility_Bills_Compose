package com.serhiiromanchuk.utilitybills.presentation.screen.add_bill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.BillItem
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
    private val insertBillItemUseCase: InsertBillItemUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(AddBillScreenState())
    val screenState: StateFlow<AddBillScreenState> = _screenState.asStateFlow()

    fun onEvent(event: AddBillScreenEvent) {
        when (event) {
            is AddBillScreenEvent.ApartmentChanged -> {
                _screenState.update {
                    it.copy(apartment = event.apartment)
                }
            }
            is AddBillScreenEvent.BuildingChanged -> {
                _screenState.update {
                    it.copy(building = event.building)
                }
            }
            is AddBillScreenEvent.HouseChanged -> {
                _screenState.update {
                    it.copy(house = event.house)
                }
            }
            is AddBillScreenEvent.StreetChanged -> {
                _screenState.update {
                    it.copy(street = event.street)
                }
            }

            is AddBillScreenEvent.Submit -> { submitData(event.address) }
        }
    }

    private fun submitData(address: String) {
        viewModelScope.launch {
            insertBillItemUseCase(
                BillItem(
                    address = address,
                    month = getCurrentMonth(),
                    year = getCurrentYear(),
                    cardNumber = ""
                )
            )
        }
    }
}