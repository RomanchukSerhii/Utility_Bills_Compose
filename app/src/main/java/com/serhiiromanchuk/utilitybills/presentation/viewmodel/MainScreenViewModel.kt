package com.serhiiromanchuk.utilitybills.presentation.viewmodel

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.DeleteBillItemUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.GetBillItemsUseCase
import com.serhiiromanchuk.utilitybills.domain.usecase.bill.InsertBillItemUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    insertBillItemUseCase: InsertBillItemUseCase,
    private val deleteBillItemUseCase: DeleteBillItemUseCase,
    private val getBillItemsUseCase: GetBillItemsUseCase
) : ViewModel() {

    fun insertBillItem(billItem: BillItem) {
        viewModelScope.launch {
        }
    }

    fun deleteBillItem() {
        getBillItemsUseCase().onEach {
            it.forEach { billItem ->
                deleteBillItemUseCase(billItem.id)
            }
        }.launchIn(viewModelScope)
    }

}