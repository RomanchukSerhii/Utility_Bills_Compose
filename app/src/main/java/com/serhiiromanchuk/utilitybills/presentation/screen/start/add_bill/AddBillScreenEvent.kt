package com.serhiiromanchuk.utilitybills.presentation.screen.start.add_bill

sealed interface AddBillScreenEvent {
    data class StreetChanged(val street: String) : AddBillScreenEvent
    data class HouseChanged(val house: String) : AddBillScreenEvent
    data class BuildingChanged(val building: String) : AddBillScreenEvent
    data class ApartmentChanged(val apartment: String) : AddBillScreenEvent
    data class Submit(val address: String) : AddBillScreenEvent
}