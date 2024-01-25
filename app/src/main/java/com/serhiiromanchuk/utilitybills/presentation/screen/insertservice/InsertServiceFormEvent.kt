package com.serhiiromanchuk.utilitybills.presentation.screen.insertservice

import com.serhiiromanchuk.utilitybills.domain.model.MeasurementUnit

sealed class InsertServiceFormEvent() {
    data class NameChanged(val name: String) : InsertServiceFormEvent()
    data class TariffChanged(val tariff: String) : InsertServiceFormEvent()
    data class MeterAvailableChanged(val isMeterAvailable: Boolean) : InsertServiceFormEvent()
    data class PreviousValueChanged(val previousValue: String) : InsertServiceFormEvent()
    data class UnitOfMeasurementChanged(val unitOfMeasurement: MeasurementUnit) : InsertServiceFormEvent()
    data object Submit : InsertServiceFormEvent()
}
