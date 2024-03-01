package com.serhiiromanchuk.utilitybills.presentation.screen.start.edit_package

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.usecase.bill_package.UpdatePackageNameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditPackageViewModel @Inject constructor(
    private val packageId: Long,
    private val packageName: String,
    private val updatePackageNameUseCase: UpdatePackageNameUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(EditPackageScreenState(packageName = packageName))
    val screenState: StateFlow<EditPackageScreenState> = _screenState.asStateFlow()

    fun onEvent(event: EditPackageScreenEvent) {
        when (event) {
            is EditPackageScreenEvent.AddressChanged -> {
                if (event.packageName != packageName) {
                    _screenState.update { it.copy(isSubmitButtonEnable = true) }
                } else {
                    _screenState.update { it.copy(isSubmitButtonEnable = false) }
                }

                _screenState.update { it.copy(packageName = event.packageName, textSelection = event.selection) }
            }

            EditPackageScreenEvent.Submit -> {
                viewModelScope.launch {
                    updatePackageNameUseCase(screenState.value.packageName, packageId)
                }
            }
        }
    }
}