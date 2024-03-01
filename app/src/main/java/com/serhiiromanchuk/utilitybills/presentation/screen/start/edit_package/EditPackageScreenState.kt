package com.serhiiromanchuk.utilitybills.presentation.screen.start.edit_package

import androidx.compose.ui.text.TextRange

data class EditPackageScreenState(
    val packageName: String,
    val textSelection: TextRange = TextRange(packageName.length),
    val isSubmitButtonEnable: Boolean = false
)