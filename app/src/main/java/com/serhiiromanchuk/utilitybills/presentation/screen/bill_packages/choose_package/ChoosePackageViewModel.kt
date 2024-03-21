package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.BillPackage
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.DeleteBillsFromPackageUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill_package.DeleteBillPackageUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill_package.GetBillPackagesUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill_package.UpdateBillPackagesUseCase
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiEvent.AddPackageClicked
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiEvent.ChangeEditMode
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiEvent.CloseBottomSheet
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiEvent.CloseDialog
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiEvent.DeletePackage
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiEvent.EditPackageClicked
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiEvent.MovePackage
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiEvent.OpenBottomSheet
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiEvent.OpenDialog
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiEvent.PackageClicked
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiEvent.SetInitialState
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiState.DialogState
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiState.PackageCardState
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiState.VisibleSheetState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _navigationEvent = MutableSharedFlow<ChoosePackageNavigationEvent>()
    val navigationEvent: SharedFlow<ChoosePackageNavigationEvent> = _navigationEvent.asSharedFlow()

    private val _screenState = MutableStateFlow(ChoosePackageUiState())
    val screenState: StateFlow<ChoosePackageUiState> = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            getBillPackagesUseCase().collect { billPackages ->
                _screenState.update { it.copy(packageList = billPackages) }
            }
        }
    }

    fun onEvent(event: ChoosePackageUiEvent) {
        when (event) {
            is ChangeEditMode -> { changeEditMode() }

            is DeletePackage -> { deletePackage(event.packageId) }

            CloseDialog -> {
                _screenState.update { state -> state.copy(dialogState = DialogState.Close) }
            }

            is OpenDialog -> {
                _screenState.update { state -> state.copy(dialogState = DialogState.Open(event.id)) }
            }

            CloseBottomSheet -> {
                _screenState.update { state ->
                    state.copy(visibleSheetState = VisibleSheetState.Close)
                }
            }

            is OpenBottomSheet -> {
                _screenState.update { state ->
                    state.copy(
                        visibleSheetState = VisibleSheetState.Open(
                            event.packageAddress,
                            event.packageId
                        )
                    )
                }
            }

            SetInitialState -> { setInitialState() }

            is MovePackage -> {
                val billItems = _screenState.value.packageList.toMutableList()
                billItems.add(event.toIndex, billItems.removeAt(event.fromIndex))
                _screenState.update { it.copy(packageList = billItems) }
            }

            AddPackageClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(ChoosePackageNavigationEvent.AddPackageClicked)
                }
            }

            is PackageClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(ChoosePackageNavigationEvent.PackageClicked(event.packageId))
                }
            }

            is EditPackageClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(
                        ChoosePackageNavigationEvent.EditPackageClicked(
                            event.packageName,
                            event.packageId
                        )
                    )
                }
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