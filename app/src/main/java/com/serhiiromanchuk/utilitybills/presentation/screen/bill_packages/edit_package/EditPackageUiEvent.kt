package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.edit_package

import androidx.compose.ui.text.TextRange

sealed interface EditPackageUiEvent {

    data object Submit : EditPackageUiEvent

    data object BackClicked : EditPackageUiEvent

    data class AddressChanged(val packageName: String, val selection: TextRange) : EditPackageUiEvent
}