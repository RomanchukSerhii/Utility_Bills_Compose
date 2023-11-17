package com.serhiiromanchuk.utilitybills.domain.usecase.bill

import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository

class DeleteBillItemUseCase(
    private val repository: BillRepository
) {

    suspend operator fun invoke(billItemId: Int) {
        repository.deleteBillItem(billItemId)
    }
}