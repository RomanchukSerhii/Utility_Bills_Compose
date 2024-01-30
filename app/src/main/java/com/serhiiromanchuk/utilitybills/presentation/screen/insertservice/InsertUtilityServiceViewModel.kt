package com.serhiiromanchuk.utilitybills.presentation.screen.insertservice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.domain.usecase.insert_service.ValidateNameUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.insert_service.ValidatePreviousValueUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.insert_service.ValidateTariffUseCase
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

class InsertUtilityServiceViewModel @Inject constructor(
    @Named("utilityServiceId") private val utilityServiceId: Long,
    @Named("billCreatorId") private val billCreatorId: Long,
    private val insertUtilityServiceUseCase: InsertUtilityServiceUseCase,
    private val getUtilityServiceUseCase: GetUtilityServiceUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validatePreviousValueUseCase: ValidatePreviousValueUseCase,
    private val validateTariffUseCase: ValidateTariffUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(InsertServiceFormState())
    val screenState: StateFlow<InsertServiceFormState> = _screenState.asStateFlow()

    private val _validationEvents = MutableSharedFlow<ValidationEvent>()
    val validationEvents = _validationEvents.asSharedFlow()

    init {
        if (utilityServiceId >= 0) {
            getUtilityService(utilityServiceId)
        }
    }

    fun onEvent(event: InsertServiceFormEvent) {
        when (event) {
            is InsertServiceFormEvent.NameChanged -> {
                _screenState.update {
                    it.copy(name = event.name)
                }
            }

            is InsertServiceFormEvent.MeterAvailableChanged -> {
                _screenState.update {
                    it.copy(isMeterAvailable = event.isMeterAvailable)
                }
            }

            is InsertServiceFormEvent.PreviousValueChanged -> {
                _screenState.update {
                    it.copy(previousValue = event.previousValue)
                }
            }

            is InsertServiceFormEvent.TariffChanged -> {
                _screenState.update {
                    it.copy(tariff = event.tariff)
                }
            }

            is InsertServiceFormEvent.UnitOfMeasurementChanged -> {
                _screenState.update {
                    it.copy(unitOfMeasurement = event.unitOfMeasurement)
                }
            }

            InsertServiceFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val validateNameResult = validateNameUseCase(_screenState.value.name)
        val validateTariffResult = validateTariffUseCase(_screenState.value.tariff)
        val validatePreviousValueResult =
            validatePreviousValueUseCase(_screenState.value.previousValue)

        val hasError = listOf(
            validateNameResult,
            validateTariffResult,
            validatePreviousValueResult
        ).any { !it.successful }

        if (hasError) {
            _screenState.update {
                it.copy(
                    nameError = validateNameResult.errorMessage,
                    tariffError = validateTariffResult.errorMessage,
                    previousValueError = validatePreviousValueResult.errorMessage
                )
            }
        } else {
            viewModelScope.launch {
                insertUtilityService()
                _validationEvents.emit(ValidationEvent.Success)
            }
        }
    }

    private suspend fun insertUtilityService() {
        val utilityService = with(screenState.value) {
            UtilityServiceItem(
                id = if (utilityServiceId >= 0) utilityServiceId else 0,
                billCreatorId = billCreatorId,
                name = name,
                tariff = tariff.removeCurrencySign().toDouble(),
                isMeterAvailable = isMeterAvailable,
                previousValue = previousValue,
                unitOfMeasurement = unitOfMeasurement
            )
        }

        insertUtilityServiceUseCase(utilityService)
    }

    private fun getUtilityService(utilityServiceId: Long) {
        viewModelScope.launch {
            val utilityService = getUtilityServiceUseCase(utilityServiceId)
            _screenState.value = InsertServiceFormState(
                name = utilityService.name,
                tariff = utilityService.tariff.toString(),
                isMeterAvailable = utilityService.isMeterAvailable,
                previousValue = utilityService.previousValue,
                unitOfMeasurement = utilityService.unitOfMeasurement
            )
        }
    }

    sealed class ValidationEvent {
        data object Success : ValidationEvent()
    }
}