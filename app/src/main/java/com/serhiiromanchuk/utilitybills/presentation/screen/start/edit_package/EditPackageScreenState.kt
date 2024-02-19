package com.serhiiromanchuk.utilitybills.presentation.screen.start.edit_package

data class EditPackageScreenState(
    val address: String,
    val isSubmitButtonAvailable: Boolean = false
)