package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.Bill
import com.serhiiromanchuk.utilitybills.domain.model.BillPackage
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.InsertBillItemUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill_package.GetLastBillPackageIdUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill_package.GetMaxIndexPositionUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill_package.InsertBillPackageUseCase
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package.AddPackageUiEvent.ApartmentChanged
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package.AddPackageUiEvent.BuildingChanged
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package.AddPackageUiEvent.CityChanged
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package.AddPackageUiEvent.ClickBack
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package.AddPackageUiEvent.HouseChanged
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package.AddPackageUiEvent.PayerNameChanged
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package.AddPackageUiEvent.StreetChanged
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package.AddPackageUiEvent.Submit
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class AddPackageViewModel @Inject constructor(
    private val insertBillPackageUseCase: InsertBillPackageUseCase,
    private val insertBillItemUseCase: InsertBillItemUseCase,
    private val getLastBillPackageIdUseCase: GetLastBillPackageIdUseCase,
    private val getMaxIndexPositionUseCase: GetMaxIndexPositionUseCase
) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<AddPackageNavigationEvent>()
    val navigationEvent: SharedFlow<AddPackageNavigationEvent> = _navigationEvent.asSharedFlow()

    private val _screenState = MutableStateFlow(AddPackageUiState())
    val screenState: StateFlow<AddPackageUiState> = _screenState.asStateFlow()

    fun onEvent(event: AddPackageUiEvent) {
        when (event) {
            is PayerNameChanged -> { _screenState.update { it.copy(payerName = event.payerName) } }

            is CityChanged -> { _screenState.update { it.copy(city = event.city) } }

            is StreetChanged -> { _screenState.update { it.copy(street = event.street) } }

            is ApartmentChanged -> { _screenState.update { it.copy(apartment = event.apartment) } }

            is BuildingChanged -> { _screenState.update { it.copy(building = event.building) } }

            is HouseChanged -> { _screenState.update { it.copy(house = event.house) } }

            is Submit -> { submitData(event.address, event.payerName) }

            ClickBack -> {
                viewModelScope.launch {
                    _navigationEvent.emit(AddPackageNavigationEvent.ClickBack)
                }
            }
        }
    }

    private fun submitData(address: String, payerName: String) {

        val deferredLastPackageIndex = viewModelScope.async {
            getMaxIndexPositionUseCase()
        }

        val insertPackageJob = viewModelScope.launch {
            val lastIndex = deferredLastPackageIndex.await()
            insertBillPackageUseCase(
                BillPackage(
                    name = address,
                    payerName = payerName,
                    address = address,
                    indexPosition = lastIndex?.let { it + 1 } ?: 0
                )
            )
        }

        val getPackageIdJob = viewModelScope.async {
            insertPackageJob.join()
            getLastBillPackageIdUseCase() ?: throw Exception("BillPackageDbModel table is empty")
        }

        val createBlankBillsJob = viewModelScope.launch {
            val packageId = getPackageIdJob.await()
            createBlankBills(packageId)
        }

        viewModelScope.launch {
            createBlankBillsJob.join()
            _navigationEvent.emit(AddPackageNavigationEvent.ClickBack)
        }
    }

    private suspend fun createBlankBills(packageId: Long) {
        val currentDate = LocalDate.now()
        repeat(12) {
            val bill = Bill(
                packageCreatorId = packageId,
                date = currentDate.minusMonths(it.toLong())
            )
            insertBillItemUseCase(bill)
        }
    }
}