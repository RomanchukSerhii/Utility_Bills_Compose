package com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_package

sealed interface ChoosePackageEvent {
    data object ChangeEditMode : ChoosePackageEvent

    data class OpenBottomSheet(val packageAddress: String, val packageId: Long) : ChoosePackageEvent

    data object CloseBottomSheet : ChoosePackageEvent

    data class DeletePackage(val packageId: Long) : ChoosePackageEvent

    data class MovePackage(val fromIndex: Int, val toIndex: Int) : ChoosePackageEvent

    data class OpenDialog(val id: Long) : ChoosePackageEvent

    data object CloseDialog : ChoosePackageEvent

    data object SetInitialState : ChoosePackageEvent
}