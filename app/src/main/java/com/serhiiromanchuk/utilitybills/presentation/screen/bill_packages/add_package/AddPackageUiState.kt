package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package

data class AddPackageUiState(
    val payerName: String = "",
    val city: String = "",
    val street: String = "",
    val house: String = "",
    val building: String = "",
    val apartment: String = ""
) {
    val isNextButtonAvailable: Boolean =
        payerName.isNotEmpty() && city.isNotEmpty() && street.isNotEmpty() && house.isNotEmpty()
}