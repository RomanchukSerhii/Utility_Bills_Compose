package com.serhiiromanchuk.utilitybills.presentation.screen.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.GetBillItemsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class StartScreenViewModel @Inject constructor(
    getBillItemsUseCase: GetBillItemsUseCase
) : ViewModel() {

    val screenState = getBillItemsUseCase()
        .map { StartScreenState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = StartScreenState()
        )

    fun onEvent(event: StartScreenEvent) {

    }
}