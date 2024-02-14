package com.serhiiromanchuk.utilitybills.presentation.screen.choose_bill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.GetBillItemsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class ChooseBillViewModel @Inject constructor(
    getBillItemsUseCase: GetBillItemsUseCase
) : ViewModel() {

    val screenState = getBillItemsUseCase()
        .map { ChooseBillState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ChooseBillState()
        )
}