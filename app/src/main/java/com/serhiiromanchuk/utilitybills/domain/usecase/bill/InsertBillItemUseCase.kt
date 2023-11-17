package com.serhiiromanchuk.utilitybills.domain.usecase.bill

import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository

class InsertBillItemUseCase(
    private val repository: BillRepository
) {
    suspend operator fun invoke(billItem: BillItem) {
        repository.insertBillItem(billItem)
    }
}