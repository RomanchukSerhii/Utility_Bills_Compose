package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package

sealed interface ChoosePackageNavigationEvent {

    data object AddPackageClicked : ChoosePackageNavigationEvent

    data class PackageClicked(val packageId: Long) : ChoosePackageNavigationEvent

    data class EditPackageClicked(val packageName: String, val packageId: Long) : ChoosePackageNavigationEvent
}