package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package

sealed interface AddPackageUiEvent {
    data class PayerNameChanged(val payerName: String) : AddPackageUiEvent
    data class CityChanged(val city: String) : AddPackageUiEvent
    data class StreetChanged(val street: String) : AddPackageUiEvent
    data class HouseChanged(val house: String) : AddPackageUiEvent
    data class BuildingChanged(val building: String) : AddPackageUiEvent
    data class ApartmentChanged(val apartment: String) : AddPackageUiEvent
    data class Submit(val address: String, val payerName: String) : AddPackageUiEvent
}