package com.serhiiromanchuk.utilitybills.presentation.screen.insert_service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.domain.usecase.utility_service.GetUtilityServiceUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.utility_service.InsertUtilityServiceUseCase
import com.serhiiromanchuk.utilitybills.utils.removeCurrencySign
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class InsertServiceViewModel @Inject constructor(
    @Named("utilityServiceId") private val utilityServiceId: Long,
    @Named("billCreatorId") private val billCreatorId: Long,
    private val insertUtilityServiceUseCase: InsertUtilityServiceUseCase,
    private val getUtilityServiceUseCase: GetUtilityServiceUseCase,
) : ViewModel() {

    private val _screenState = MutableStateFlow(InsertServiceUiState())
    val screenState: StateFlow<InsertServiceUiState> = _screenState.asStateFlow()

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    init {
        if (utilityServiceId >= 0) setUtilityServiceInfo(utilityServiceId)
    }

    fun onEvent(event: InsertServiceUiEvent) {
        when (event) {
            is InsertServiceUiEvent.NameChanged -> {
                _screenState.update { it.copy(serviceName = event.name) }
            }

            is InsertServiceUiEvent.MeterAvailableChanged -> {
                _screenState.update { it.copy(isMeterAvailable = event.isMeterAvailable) }
            }

            is InsertServiceUiEvent.PreviousValueChanged -> {
                _screenState.update { it.copy(previousValue = event.previousValue) }
            }

            is InsertServiceUiEvent.CurrentValueChanged -> {
                _screenState.update { it.copy(currentValue = event.currentValue) }
            }

            is InsertServiceUiEvent.TariffChanged -> {
                _screenState.update { it.copy(tariff = event.tariff) }
            }

            is InsertServiceUiEvent.UnitOfMeasurementChanged -> {
                _screenState.update { it.copy(unitOfMeasurement = event.unitOfMeasurement) }
            }

            InsertServiceUiEvent.Submit -> {
                viewModelScope.launch {
                    insertUtilityService()
                    _navigationEvents.emit(NavigationEvent.OnBack)
                }
            }

            InsertServiceUiEvent.OnBackClicked -> {
                viewModelScope.launch {
                    _navigationEvents.emit(NavigationEvent.OnBack)
                }
            }
        }
    }

    private suspend fun insertUtilityService() {
        val utilityService = with(screenState.value) {
            UtilityServiceItem(
                id = if (utilityServiceId >= 0) utilityServiceId else 0,
                billCreatorId = billCreatorId,
                name = serviceName,
                tariff = tariff.removeCurrencySign().toDouble(),
                isMeterAvailable = isMeterAvailable,
                previousValue = previousValue,
                currentValue = currentValue,
                unitOfMeasurement = unitOfMeasurement
            )
        }

        insertUtilityServiceUseCase(utilityService)
    }

    private fun setUtilityServiceInfo(utilityServiceId: Long) {
        viewModelScope.launch {
            val utilityService = getUtilityServiceUseCase(utilityServiceId)
            _screenState.value = InsertServiceUiState(
                serviceName = utilityService.name,
                tariff = utilityService.tariff.toString(),
                isMeterAvailable = utilityService.isMeterAvailable,
                previousValue = utilityService.previousValue,
                unitOfMeasurement = utilityService.unitOfMeasurement
            )
        }
    }

    sealed interface NavigationEvent {
        data object OnBack : NavigationEvent
    }
}