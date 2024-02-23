package com.serhiiromanchuk.utilitybills.presentation.screen.start.edit_package

import androidx.compose.ui.text.TextRange

data class EditPackageScreenState(
    val address: String,
    val addressSelection: TextRange = TextRange(address.length),
    val isSubmitButtonEnable: Boolean = false
)