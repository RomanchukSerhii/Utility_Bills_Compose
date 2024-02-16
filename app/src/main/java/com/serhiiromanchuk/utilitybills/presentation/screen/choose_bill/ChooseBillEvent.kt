package com.serhiiromanchuk.utilitybills.presentation.screen.choose_bill

sealed interface ChooseBillEvent {
    data object ChangeEditMode : ChooseBillEvent

    data object ChangeBottomSheetState : ChooseBillEvent
}