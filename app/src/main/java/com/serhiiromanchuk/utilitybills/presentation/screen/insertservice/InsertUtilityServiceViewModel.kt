package com.serhiiromanchuk.utilitybills.presentation.screen.insertservice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.domain.usecase.insert_service.ValidateNameUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.insert_service.ValidatePreviousValueUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.insert_service.ValidateTariffUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.utility_service.GetUtilityServiceUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.utility_service.InsertUtilityServiceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class InsertUtilityServiceViewModel @Inject constructor(
    private val utilityServiceId: Int,
    private val insertUtilityServiceUseCase: InsertUtilityServiceUseCase,
    private val getUtilityServiceUseCase: GetUtilityServiceUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validatePreviousValueUseCase: ValidatePreviousValueUseCase,
    private val validateTariffUseCase: ValidateTariffUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(InsertServiceFormState())
    val screenState: StateFlow<InsertServiceFormState> = _screenState.asStateFlow()

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
        val validatePreviousValueResult = validatePreviousValueUseCase(_screenState.value.previousValue)

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
            insertUtilityService()
        }
    }

    private fun insertUtilityService() {
        val utilityService = with(screenState.value) {
            UtilityServiceItem(
                id = if (utilityServiceId >= 0 ) utilityServiceId else 0,
                name = name,
                tariff = tariff.toDouble(),
                isMeterAvailable = isMeterAvailable,
                previousValue = previousValue,
                unitOfMeasurement = unitOfMeasurement
            )
        }

        viewModelScope.launch {
            insertUtilityServiceUseCase(utilityService)
        }
    }


//    fun onNameChanged(name: String) {
//        _screenState.update {
//            it.copy(name = name)
//        }
//    }
//
//    fun onTariffChanged(tariff: String) {
//        _screenState.update {
//            it.copy(tariff = tariff)
//        }
//    }
//
//    fun onMeterAvailableChanged(isMeterAvailable: Boolean) {
//        _screenState.update {
//            it.copy(isMeterAvailable = isMeterAvailable)
//        }
//    }
//
//    fun onPreviousValueChanged(previousValue: String) {
//        _screenState.update {
//            it.copy(previousValue = previousValue)
//        }
//    }
//
//    fun onMeasurementUnitChanged(measurementUnit: MeasurementUnit) {
//        _screenState.update {
//            it.copy(unitOfMeasurement = measurementUnit)
//        }
//    }


    private fun getUtilityService(utilityServiceId: Int) {
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

//    fun insertUtilityService(
//        address: String,
//        name: String,
//        tariff: String,
//        isMeterAvailable: Boolean,
//        unitOfMeasurement: MeasurementUnit,
//        previousValue: String = "0"
//    ) {
//        val isNameEmpty = name.isEmpty()
//        val isTariffEmpty = tariff.isEmpty()
//        val isTariffNotDouble = tariff.toDoubleOrNull() == null
//        val isPreviousValueEmpty = previousValue.isEmpty()
//        val isPreviousValueNotDigit = !previousValue.isDigitsOnly()
//
//        if (isNameEmpty || isTariffEmpty || isTariffNotDouble || isPreviousValueEmpty || isPreviousValueNotDigit) {
//            _screenUiState.value = InsertUtilityServiceScreenState.Errors(
//                isNameEmpty = isNameEmpty,
//                isTariffEmpty = isTariffEmpty,
//                isTariffNotDouble = isTariffNotDouble,
//                isPreviousValueEmpty = isPreviousValueEmpty,
//                isPreviousValueNotDigit = isPreviousValueNotDigit
//            )
//        } else {
//            viewModelScope.launch {
//                insertUtilityServiceUseCase(
//                    UtilityServiceItem(
//                        name = name,
//                        tariff = tariff.toDouble(),
//                        isMeterAvailable = isMeterAvailable,
//                        previousValue = previousValue,
//                        unitOfMeasurement = unitOfMeasurement
//                    )
//                )
//            }
//        }
//    }


}

//data class InsertUtilityServiceScreenState (
//    val name: String = "",
//    val tariff: String = "",
//    val isMeterAvailable: Boolean = false,
//    val previousValue: String = "",
//    val unitOfMeasurement: MeasurementUnit = MeasurementUnit.CUBIC_METER,
//    val isNameEmpty: Boolean = false,
//    val isTariffEmpty: Boolean = false,
//    val isTariffNotDouble: Boolean = false,
//    val isPreviousValueEmpty: Boolean = false,
//    val isPreviousValueNotDigit: Boolean = false
//)