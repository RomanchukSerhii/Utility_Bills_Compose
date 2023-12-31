package com.serhiiromanchuk.utilitybills.domain.usecase.bill

import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBillItemsUseCase @Inject constructor(
    private val repository: BillRepository
) {

    operator fun invoke(): Flow<List<BillItem>> {
        return repository.getBillItems()
    }
}