package com.serhiiromanchuk.utilitybills.presentation.viewmodel

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.InsertBillItemUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    insertBillItemUseCase: InsertBillItemUseCase
) : ViewModel() {

    fun insertBillItem(billItem: BillItem) {
        viewModelScope.launch {
        }
    }

}