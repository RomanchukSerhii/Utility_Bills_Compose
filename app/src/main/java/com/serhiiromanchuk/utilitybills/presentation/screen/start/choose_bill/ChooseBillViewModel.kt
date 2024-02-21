package com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.DeleteBillItemUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.GetBillItemsUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.UpdateBillItemsUseCase
import com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill.ChooseBillState.BillCardState
import com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill.ChooseBillState.DialogState
import com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill.ChooseBillState.VisibleSheetState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChooseBillViewModel @Inject constructor(
    getBillItemsUseCase: GetBillItemsUseCase,
    private val deleteBillItemUseCase: DeleteBillItemUseCase,
    private val updateBillItemsUseCase: UpdateBillItemsUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(ChooseBillState())
    val screenState: StateFlow<ChooseBillState> = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            getBillItemsUseCase().collect { billList ->
                _screenState.update { it.copy(billList = billList) }
            }
        }
    }

    fun onEvent(event: ChooseBillEvent) {
        when (event) {
            is ChooseBillEvent.ChangeEditMode -> {
                _screenState.update { state ->
                    val isEditMode = !state.isEditMode
                    state.copy(
                        isEditMode = isEditMode,
                        billCardState = if (isEditMode) {
                            BillCardState.EditMode
                        } else BillCardState.Initial,
                        visibleSheetState = VisibleSheetState.Close
                    )
                }
            }

            is ChooseBillEvent.DeleteBill -> {
                viewModelScope.launch {
                    deleteBillItemUseCase(event.id)
                    _screenState.update { state ->
                        state.copy(
                            isEditMode = false,
                            billCardState = BillCardState.Initial,
                            dialogState = DialogState.Close
                        )
                    }
                }
            }

            ChooseBillEvent.CloseDialog -> {
                _screenState.update { state -> state.copy(dialogState = DialogState.Close) }
            }

            is ChooseBillEvent.OpenDialog -> {
                _screenState.update { state -> state.copy(dialogState = DialogState.Open(event.id)) }
            }

            ChooseBillEvent.CloseBottomSheet -> {
                _screenState.update { state ->
                    state.copy(visibleSheetState = VisibleSheetState.Close)
                }
            }

            is ChooseBillEvent.OpenBottomSheet -> {
                _screenState.update { state ->
                    state.copy(
                        visibleSheetState = VisibleSheetState.Open(event.billAddress, event.billId)
                    )
                }
            }

            ChooseBillEvent.SetInitialState -> {
                _screenState.update {
                    it.copy(
                        isEditMode = false,
                        billCardState = BillCardState.Initial,
                        dialogState = DialogState.Close,
                        visibleSheetState = VisibleSheetState.Close
                    )
                }
            }

            is ChooseBillEvent.MoveBill -> {
                val billItems = _screenState.value.billList.toMutableList()
                billItems.add(event.toIndex, billItems.removeAt(event.fromIndex))
                _screenState.update { it.copy(billList = billItems) }
            }
        }
    }

    override fun onCleared() {
        val billItems = _screenState.value.billList.toMutableList()
        val updateBillList = mutableListOf<BillItem>()
        billItems.forEachIndexed() { index, billItem ->
            updateBillList.add(index, billItem.copy(id = index.toLong()))
        }
        viewModelScope.launch {
            updateBillItemsUseCase(updateBillList)
        }

        super.onCleared()
    }
}