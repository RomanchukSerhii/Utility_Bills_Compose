package com.serhiiromanchuk.utilitybills.presentation.screen.main

import androidx.lifecycle.ViewModel
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.GetBillWithUtilityServicesUseCase
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val getBillWithUtilityServicesUseCase: GetBillWithUtilityServicesUseCase
) : ViewModel() {


}