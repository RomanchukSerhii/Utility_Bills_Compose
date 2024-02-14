package com.serhiiromanchuk.utilitybills.presentation.screen.choose_bill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.GetBillItemsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChooseBillViewModel @Inject constructor(
    getBillItemsUseCase: GetBillItemsUseCase
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
                        isEditMode = !state.isEditMode
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
        }
    }
}