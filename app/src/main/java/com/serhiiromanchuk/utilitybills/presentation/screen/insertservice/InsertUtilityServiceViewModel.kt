package com.serhiiromanchuk.utilitybills.presentation.screen.insertservice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.MeasurementUnit
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
    private val getUtilityServiceUseCase: GetUtilityServiceUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(InsertServiceFormState())
    val screenState: StateFlow<InsertServiceFormState> = _screenState.asStateFlow()

    init {
        if (utilityServiceId >= 0) {
            getUtilityService(utilityServiceId)
        }
    }


    fun onNameChanged(name: String) {
        _screenState.update {
            it.copy(name = name)
        }
    }

    fun onTariffChanged(tariff: String) {
        _screenState.update {
            it.copy(tariff = tariff)
        }
    }

    fun onMeterAvailableChanged(isMeterAvailable: Boolean) {
        _screenState.update {
            it.copy(isMeterAvailable = isMeterAvailable)
        }
    }

    fun onPreviousValueChanged(previousValue: String) {
        _screenState.update {
            it.copy(previousValue = previousValue)
        }
    }

    fun onMeasurementUnitChanged(measurementUnit: MeasurementUnit) {
        _screenState.update {
            it.copy(unitOfMeasurement = measurementUnit)
        }
    }


    private fun getUtilityService(utilityServiceId: Int) {
        viewModelScope.launch {
            val utilityService = getUtilityServiceUseCase(utilityServiceId)
            _screenState.value = InsertUtilityServiceScreenState(
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