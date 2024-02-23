package com.serhiiromanchuk.utilitybills.presentation.screen.start.add_bill

sealed interface AddBillScreenEvent {
    data class PayerNameChanged(val payerName: String) : AddBillScreenEvent
    data class CityChanged(val city: String) : AddBillScreenEvent
    data class StreetChanged(val street: String) : AddBillScreenEvent
    data class HouseChanged(val house: String) : AddBillScreenEvent
    data class BuildingChanged(val building: String) : AddBillScreenEvent
    data class ApartmentChanged(val apartment: String) : AddBillScreenEvent
    data class Submit(val address: String, val payerName: String) : AddBillScreenEvent
}