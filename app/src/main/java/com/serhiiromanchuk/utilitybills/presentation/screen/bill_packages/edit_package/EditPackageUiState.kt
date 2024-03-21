package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.edit_package

import androidx.compose.ui.text.TextRange

data class EditPackageUiState(
    val packageName: String,
    val textSelection: TextRange = TextRange(packageName.length),
    val isSubmitButtonEnable: Boolean = false
)