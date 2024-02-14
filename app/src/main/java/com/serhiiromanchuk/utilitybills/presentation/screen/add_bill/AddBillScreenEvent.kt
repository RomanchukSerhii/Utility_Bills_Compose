package com.serhiiromanchuk.utilitybills.presentation.screen.add_bill

sealed class AddBillScreenEvent {
    data class StreetChanged(val street: String) : AddBillScreenEvent()
    data class HouseChanged(val house: String) : AddBillScreenEvent()
    data class BuildingChanged(val building: String) : AddBillScreenEvent()
    data class ApartmentChanged(val apartment: String) : AddBillScreenEvent()
    data class Submit(val address: String) : AddBillScreenEvent()
}