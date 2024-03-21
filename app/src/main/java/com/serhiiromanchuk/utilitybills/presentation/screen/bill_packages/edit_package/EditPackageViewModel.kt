package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.edit_package

import androidx.compose.ui.text.TextRange
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.usecase.bill_package.UpdatePackageNameUseCase
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.edit_package.EditPackageUiEvent.AddressChanged
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.edit_package.EditPackageUiEvent.BackClicked
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.edit_package.EditPackageUiEvent.Submit
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditPackageViewModel @Inject constructor(
    private val packageId: Long,
    private val packageName: String,
    private val updatePackageNameUseCase: UpdatePackageNameUseCase
) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<EditPackageNavigationEvent>()
    val navigationEvent: SharedFlow<EditPackageNavigationEvent> = _navigationEvent.asSharedFlow()

    private val _screenState = MutableStateFlow(EditPackageUiState(packageName = packageName))
    val screenState: StateFlow<EditPackageUiState> = _screenState.asStateFlow()

    fun onEvent(event: EditPackageUiEvent) {
        when (event) {
            is AddressChanged -> { updateAddressState(event.packageName, event.selection) }

            Submit -> {
                viewModelScope.launch {
                    updatePackageNameUseCase(screenState.value.packageName, packageId)
                }
            }

            BackClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(EditPackageNavigationEvent.BackClicked)
                }
            }
        }
    }

    private fun updateAddressState(newPackageName: String, textSelection: TextRange) {
        if (newPackageName != packageName) {
            _screenState.update { it.copy(isSubmitButtonEnable = true) }
        } else {
            _screenState.update { it.copy(isSubmitButtonEnable = false) }
        }

        _screenState.update { it.copy(packageName = newPackageName, textSelection = textSelection) }
    }
}