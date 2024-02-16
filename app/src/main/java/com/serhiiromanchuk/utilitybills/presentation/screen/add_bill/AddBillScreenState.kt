package com.serhiiromanchuk.utilitybills.presentation.screen.add_bill

data class AddBillScreenState (
    val street: String = "",
    val house: String = "",
    val building: String = "",
    val apartment: String = ""
) {
    val nextButtonAvailable: Boolean = street.isNotEmpty() && house.isNotEmpty()
}