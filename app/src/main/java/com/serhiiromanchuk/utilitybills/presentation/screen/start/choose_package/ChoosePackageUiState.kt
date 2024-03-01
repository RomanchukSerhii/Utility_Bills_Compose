package com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_package

import com.serhiiromanchuk.utilitybills.domain.model.BillPackage

data class ChoosePackageUiState (
    val packageList: List<BillPackage> = listOf(),
    val isEditMode: Boolean = false,
    val packageCardState: PackageCardState = PackageCardState.Initial,
    val dialogState: DialogState = DialogState.Close,
    val visibleSheetState: VisibleSheetState = VisibleSheetState.Close
) {
    sealed interface PackageCardState {
        data object Initial : PackageCardState

        data object EditMode : PackageCardState
    }


    sealed interface DialogState {
        data class Open(val id: Long) : DialogState
        data object Close : DialogState
    }

    sealed interface VisibleSheetState {
        data class Open(val packageName: String, val packageId: Long) : VisibleSheetState
        data object Close : VisibleSheetState
    }
}


