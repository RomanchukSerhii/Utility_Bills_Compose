package com.serhiiromanchuk.utilitybills.presentation.screen.start.edit_package

import androidx.compose.ui.text.TextRange

sealed interface EditPackageScreenEvent {

    data object Submit : EditPackageScreenEvent

    data class AddressChanged(val address: String, val selection: TextRange) : EditPackageScreenEvent
}