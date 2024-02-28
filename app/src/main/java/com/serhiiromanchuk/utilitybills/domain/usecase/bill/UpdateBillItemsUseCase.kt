package com.serhiiromanchuk.utilitybills.domain.usecase.bill

import com.serhiiromanchuk.utilitybills.domain.model.Bill
import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository
import javax.inject.Inject

class UpdateBillItemsUseCase @Inject constructor(
    private val repository: BillRepository
) {

    suspend operator fun invoke(billItems: List<Bill>) {
        repository.updateBillItems(billItems)
    }
}