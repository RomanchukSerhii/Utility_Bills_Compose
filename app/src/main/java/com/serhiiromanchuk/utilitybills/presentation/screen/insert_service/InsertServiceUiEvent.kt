package com.serhiiromanchuk.utilitybills.presentation.screen.insert_service

import com.serhiiromanchuk.utilitybills.domain.model.MeasurementUnit

sealed interface InsertServiceUiEvent {
    data class NameChanged(val name: String) : InsertServiceUiEvent
    data class TariffChanged(val tariff: String) : InsertServiceUiEvent
    data class MeterAvailableChanged(val isMeterAvailable: Boolean) : InsertServiceUiEvent
    data class PreviousValueChanged(val previousValue: String) : InsertServiceUiEvent
    data class CurrentValueChanged(val currentValue: String) : InsertServiceUiEvent
    data class UnitOfMeasurementChanged(val unitOfMeasurement: MeasurementUnit) : InsertServiceUiEvent
    data object OnBackClicked : InsertServiceUiEvent
    data object Submit : InsertServiceUiEvent
}
 