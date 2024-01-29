package com.serhiiromanchuk.utilitybills.domain.usecase.bill

import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository
import javax.inject.Inject

class DeleteBillItemUseCase @Inject constructor(
    private val repository: BillRepository
) {

    suspend operator fun invoke(billItemId: Long) {
        repository.deleteBillItem(billItemId)
    }
}