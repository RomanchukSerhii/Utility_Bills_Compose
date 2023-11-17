package com.serhiiromanchuk.utilitybills.domain.usecase.bill

import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow

class GetBillItemsUseCase(
    private val repository: BillRepository
) {

    operator fun invoke(address: String): Flow<List<BillItem>> {
        return repository.getBillItems(address)
    }
}