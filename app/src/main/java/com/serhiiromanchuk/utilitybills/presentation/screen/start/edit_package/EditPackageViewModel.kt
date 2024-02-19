package com.serhiiromanchuk.utilitybills.presentation.screen.start.edit_package

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.UpdateBillAddressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditPackageViewModel @Inject constructor(
    private val id: Long,
    private val address: String,
    private val updateBillAddressUseCase: UpdateBillAddressUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(EditPackageScreenState(address = address))
    val screenState: StateFlow<EditPackageScreenState> = _screenState.asStateFlow()


    fun onEvent(event: EditPackageScreenEvent) {
        when (event) {
            is EditPackageScreenEvent.AddressChanged -> {
                if (event.address != address) {
                    _screenState.update { it.copy(isSubmitButtonAvailable = true) }
                } else {
                    _screenState.update { it.copy(isSubmitButtonAvailable = false) }
                }

                _screenState.update { it.copy(address = event.address) }
            }

            EditPackageScreenEvent.Submit -> {
                viewModelScope.launch {
                    updateBillAddressUseCase(screenState.value.address, id)
                }
            }
        }
    }
}