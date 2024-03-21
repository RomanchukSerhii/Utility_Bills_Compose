package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package

sealed interface ChoosePackageNavigationEvent {

    data object ClickAddBill : ChoosePackageNavigationEvent

    data class ClickBillItem(val packageId: Long) : ChoosePackageNavigationEvent

    data class ClickEditPackage(val packageName: String, val packageId: Long) : ChoosePackageNavigationEvent
}