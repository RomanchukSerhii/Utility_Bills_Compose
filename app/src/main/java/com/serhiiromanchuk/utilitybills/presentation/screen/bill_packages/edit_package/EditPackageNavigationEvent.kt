package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.edit_package

sealed interface EditPackageNavigationEvent {

    data object BackClicked : EditPackageNavigationEvent
}