package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package

sealed interface ChoosePackageUiEvent {
    data object ChangeEditMode : ChoosePackageUiEvent

    data class OpenBottomSheet(val packageAddress: String, val packageId: Long) : ChoosePackageUiEvent

    data object CloseBottomSheet : ChoosePackageUiEvent

    data class DeletePackage(val packageId: Long) : ChoosePackageUiEvent

    data class MovePackage(val fromIndex: Int, val toIndex: Int) : ChoosePackageUiEvent

    data class OpenDialog(val id: Long) : ChoosePackageUiEvent

    data object CloseDialog : ChoosePackageUiEvent

    data object SetInitialState : ChoosePackageUiEvent

    data object ClickAddBill : ChoosePackageUiEvent

    data class ClickBillItem(val packageId: Long) : ChoosePackageUiEvent

    data class ClickEditPackage(val packageName: String, val packageId: Long) : ChoosePackageUiEvent
}