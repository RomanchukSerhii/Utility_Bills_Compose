package com.serhiiromanchuk.utilitybills.domain.usecase.bill

import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository
import javax.inject.Inject

class InsertBillItemUseCase @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(billItem: BillItem) {
        repository.insertBillItem(billItem)
    }
}