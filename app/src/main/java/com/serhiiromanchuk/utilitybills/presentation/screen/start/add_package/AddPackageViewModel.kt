package com.serhiiromanchuk.utilitybills.presentation.screen.start.add_package

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.BillPackage
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.InsertBillItemUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill_package.GetLastBillPackageIdUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill_package.GetMaxIndexPositionUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill_package.InsertBillPackageUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddPackageViewModel @Inject constructor(
    private val insertBillPackageUseCase: InsertBillPackageUseCase,
    private val insertBillItemUseCase: InsertBillItemUseCase,
    private val getLastBillPackageIdUseCase: GetLastBillPackageIdUseCase,
    private val getMaxIndexPositionUseCase: GetMaxIndexPositionUseCase
) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent.asSharedFlow()

    private val _screenState = MutableStateFlow(AddPackageUiState())
    val screenState: StateFlow<AddPackageUiState> = _screenState.asStateFlow()

    fun onEvent(event: AddPackageUiEvent) {
        when (event) {
            is AddPackageUiEvent.PayerNameChanged -> {
                _screenState.update { it.copy(payerName = event.payerName) }
            }

            is AddPackageUiEvent.CityChanged -> {
                _screenState.update { it.copy(city = event.city) }
            }

            is AddPackageUiEvent.StreetChanged -> {
                _screenState.update { it.copy(street = event.street) }
            }

            is AddPackageUiEvent.ApartmentChanged -> {
                _screenState.update { it.copy(apartment = event.apartment) }
            }

            is AddPackageUiEvent.BuildingChanged -> {
                _screenState.update { it.copy(building = event.building) }
            }

            is AddPackageUiEvent.HouseChanged -> {
                _screenState.update { it.copy(house = event.house) }
            }

            is AddPackageUiEvent.Submit -> {
                submitData(event.address, event.payerName)
            }
        }
    }

    private fun submitData(address: String, payerName: String) {

        val deferredLastIndex = viewModelScope.async {
            getMaxIndexPositionUseCase()
        }

        val insertPackageJob = viewModelScope.launch {
            val lastIndex = deferredLastIndex.await()
            insertBillPackageUseCase(
                BillPackage(
                    name = address,
                    payerName = payerName,
                    address = address,
                    indexPosition = lastIndex?.let { it + 1 } ?: 0
                )
            )
        }

        viewModelScope.launch {
            insertPackageJob.join()
            _navigationEvent.emit(NavigationEvent.OnBack)
        }
    }

    sealed interface NavigationEvent {
        data object OnBack : NavigationEvent
    }
}