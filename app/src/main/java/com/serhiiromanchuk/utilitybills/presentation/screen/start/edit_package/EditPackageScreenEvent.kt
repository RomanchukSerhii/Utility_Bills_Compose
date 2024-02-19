package com.serhiiromanchuk.utilitybills.presentation.screen.start.edit_package

sealed interface EditPackageScreenEvent {

    data object Submit : EditPackageScreenEvent

    data class AddressChanged(val address: String) : EditPackageScreenEvent
}