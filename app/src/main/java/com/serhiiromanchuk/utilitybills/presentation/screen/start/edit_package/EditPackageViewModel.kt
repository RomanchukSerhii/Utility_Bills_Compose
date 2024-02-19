package com.serhiiromanchuk.utilitybills.presentation.screen.start.edit_package

import androidx.lifecycle.ViewModel

class EditPackageViewModel : ViewModel() {


    fun onEvent(event: EditPackageScreenEvent) {
        when (event) {
            is EditPackageScreenEvent.AddressChanged -> {


            }
        }
    }
}