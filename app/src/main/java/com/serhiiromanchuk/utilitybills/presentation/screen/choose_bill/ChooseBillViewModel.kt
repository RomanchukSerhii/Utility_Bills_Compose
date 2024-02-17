package com.serhiiromanchuk.utilitybills.presentation.screen.choose_bill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.DeleteBillItemUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.GetBillItemsUseCase
import com.serhiiromanchuk.utilitybills.presentation.screen.choose_bill.ChooseBillState.DialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChooseBillViewModel @Inject constructor(
    getBillItemsUseCase: GetBillItemsUseCase,
    private val deleteBillItemUseCase: DeleteBillItemUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(ChooseBillState())
    val screenState: StateFlow<ChooseBillState> = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            getBillItemsUseCase().collect { billList ->
                _screenState.update {
                    it.copy(
                        billList = billList
                    )
                }
            }
        }
    }

    fun onEvent(event: ChooseBillEvent) {
        when(event) {
            ChooseBillEvent.ChangeEditMode -> {
                _screenState.update { state ->
                    state.copy(
                        isEditMode = !state.isEditMode,
                        isSheetOpen = false
                    )
                }
            }
            ChooseBillEvent.ChangeBottomSheetState -> {
                _screenState.update { state ->
                    state.copy(
                        isSheetOpen = !state.isSheetOpen
                    )
                }
            }

            is ChooseBillEvent.DeleteBill -> {
                viewModelScope.launch {
                    deleteBillItemUseCase(event.id)
                    _screenState.update { state ->
                        state.copy(
                            isEditMode = !state.isEditMode,
                            dialogState = DialogState.CloseDialog
                        )
                    }
                }
            }

            ChooseBillEvent.CloseDialog -> {
                _screenState.update { state ->
                    state.copy(
                        dialogState = DialogState.CloseDialog
                    )
                }
            }
            is ChooseBillEvent.OpenDialog -> {
                _screenState.update { state ->
                    state.copy(
                        dialogState = DialogState.OpenDialog(event.id)
                    )
                }
            }
        }
    }
}