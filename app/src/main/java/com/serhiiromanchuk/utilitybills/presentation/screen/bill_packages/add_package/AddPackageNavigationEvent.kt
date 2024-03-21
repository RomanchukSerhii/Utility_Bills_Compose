package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package

sealed interface AddPackageNavigationEvent {

    data object BackClicked : AddPackageNavigationEvent
}