package com.serhiiromanchuk.utilitybills.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.serhiiromanchuk.utilitybills.domain.usecase.utilityservice.DeleteUtilityServiceUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.utilityservice.GetUtilityServicesUseCase

class MainScreenViewModel(
    private val deleteUtilityServiceUseCase: DeleteUtilityServiceUseCase,
    private val getUtilityServicesUseCase: GetUtilityServicesUseCase
) : ViewModel() {
}