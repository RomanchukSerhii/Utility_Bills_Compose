package com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_package

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.BillPackage
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.DeleteBillsFromPackageUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill_package.DeleteBillPackageUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill_package.GetBillPackagesUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill_package.UpdateBillPackagesUseCase
import com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_package.ChoosePackageState.DialogState
import com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_package.ChoosePackageState.PackageCardState
import com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_package.ChoosePackageState.VisibleSheetState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChoosePackageViewModel @Inject constructor(
    getBillPackagesUseCase: GetBillPackagesUseCase,
    private val deleteBillPackageUseCase: DeleteBillPackageUseCase,
    private val deleteBillsFromPackageUseCase: DeleteBillsFromPackageUseCase,
    private val updateBillPackagesUseCase: UpdateBillPackagesUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(ChoosePackageState())
    val screenState: StateFlow<ChoosePackageState> = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            getBillPackagesUseCase().collect { billPackages ->
                _screenState.update { it.copy(packageList = billPackages) }
            }
        }
    }

    fun onEvent(event: ChoosePackageEvent) {
        when (event) {
            is ChoosePackageEvent.ChangeEditMode -> { changeEditMode() }

            is ChoosePackageEvent.DeletePackage -> { deletePackage(event.packageId) }

            ChoosePackageEvent.CloseDialog -> {
                _screenState.update { state -> state.copy(dialogState = DialogState.Close) }
            }

            is ChoosePackageEvent.OpenDialog -> {
                _screenState.update { state -> state.copy(dialogState = DialogState.Open(event.id)) }
            }

            ChoosePackageEvent.CloseBottomSheet -> {
                _screenState.update { state ->
                    state.copy(visibleSheetState = VisibleSheetState.Close)
                }
            }

            is ChoosePackageEvent.OpenBottomSheet -> {
                _screenState.update { state ->
                    state.copy(
                        visibleSheetState = VisibleSheetState.Open(event.packageAddress, event.packageId)
                    )
                }
            }

            ChoosePackageEvent.SetInitialState -> { setInitialState() }

            is ChoosePackageEvent.MovePackage -> {
                val billItems = _screenState.value.packageList.toMutableList()
                billItems.add(event.toIndex, billItems.removeAt(event.fromIndex))
                _screenState.update { it.copy(packageList = billItems) }
            }
        }
    }

    private fun changeEditMode() {
        _screenState.update { state ->
            val isEditMode = !state.isEditMode
            state.copy(
                isEditMode = isEditMode,
                packageCardState = if (isEditMode) {
                    PackageCardState.EditMode
                } else {
                    updateBillItems()
                    PackageCardState.Initial
                },
                visibleSheetState = VisibleSheetState.Close
            )
        }
    }

    private fun deletePackage(packageId: Long) {
        viewModelScope.launch {
            deleteBillPackageUseCase(packageId)
            deleteBillsFromPackageUseCase(packageId)
            _screenState.update { state ->
                state.copy(
                    isEditMode = false,
                    packageCardState = PackageCardState.Initial,
                    dialogState = DialogState.Close
                )
            }
        }
    }

    private fun setInitialState() {
        _screenState.update {
            it.copy(
                isEditMode = false,
                packageCardState = PackageCardState.Initial,
                dialogState = DialogState.Close,
                visibleSheetState = VisibleSheetState.Close
            )
        }
    }

    private fun updateBillItems() {
        val billPackages = _screenState.value.packageList.toMutableList()
        val updateBillPackages = mutableListOf<BillPackage>()
        billPackages.forEachIndexed { index, billItem ->
            updateBillPackages.add(index, billItem.copy(indexPosition = index))
        }
        viewModelScope.launch {
            updateBillPackagesUseCase(updateBillPackages)
        }
    }
}