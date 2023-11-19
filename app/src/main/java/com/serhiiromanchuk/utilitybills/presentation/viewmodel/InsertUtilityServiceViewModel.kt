package com.serhiiromanchuk.utilitybills.presentation.viewmodel

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.MeasurementUnit
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.domain.usecase.utilityservice.GetUtilityServiceUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.utilityservice.InsertUtilityServiceUseCase
import com.serhiiromanchuk.utilitybills.presentation.screen.InsertUtilityServiceScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InsertUtilityServiceViewModel(
    private val insertUtilityServiceUseCase: InsertUtilityServiceUseCase,
    private val getUtilityServiceUseCase: GetUtilityServiceUseCase
) : ViewModel() {

    private val _screenUiState =
        MutableStateFlow<InsertUtilityServiceScreenUiState>(InsertUtilityServiceScreenUiState.Initial())
    val screenUiState: StateFlow<InsertUtilityServiceScreenUiState> = _screenUiState.asStateFlow()

    fun getUtilityService(utilityServiceId: Int) {
        viewModelScope.launch {
            val utilityService = getUtilityServiceUseCase(utilityServiceId)
            _screenUiState.value = InsertUtilityServiceScreenUiState.Content(
                name = utilityService.name,
                tariff = utilityService.tariff.toString(),
                isMeterAvailable = utilityService.isMeterAvailable,
                previousValue = utilityService.previousValue.toString(),
                unitOfMeasurement = utilityService.unitOfMeasurement
            )
        }
    }

    fun insertUtilityService(
        address: String,
        name: String,
        tariff: String,
        isMeterAvailable: Boolean,
        unitOfMeasurement: MeasurementUnit,
        previousValue: String = "0"
    ) {
        val isNameEmpty = name.isEmpty()
        val isTariffEmpty = tariff.isEmpty()
        val isTariffNotDouble = tariff.toDoubleOrNull() == null
        val isPreviousValueEmpty = previousValue.isEmpty()
        val isPreviousValueNotDigit = !previousValue.isDigitsOnly()

        if (isNameEmpty || isTariffEmpty || isTariffNotDouble || isPreviousValueEmpty || isPreviousValueNotDigit) {
            _screenUiState.value = InsertUtilityServiceScreenUiState.Error(
                isNameEmpty = isNameEmpty,
                isTariffEmpty = isTariffEmpty,
                isTariffNotDouble = isTariffNotDouble,
                isPreviousValueEmpty = isPreviousValueEmpty,
                isPreviousValueNotDigit = isPreviousValueNotDigit
            )
        } else {
            viewModelScope.launch {
                insertUtilityServiceUseCase(
                    UtilityServiceItem(
                        address = address,
                        name = name,
                        tariff = tariff.toDouble(),
                        isMeterAvailable = isMeterAvailable,
                        previousValue = previousValue.toInt(),
                        unitOfMeasurement = unitOfMeasurement
                    )
                )
            }
        }
    }
}