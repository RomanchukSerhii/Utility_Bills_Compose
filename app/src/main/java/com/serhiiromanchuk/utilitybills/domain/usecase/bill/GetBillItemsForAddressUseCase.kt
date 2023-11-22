package com.serhiiromanchuk.utilitybills.domain.usecase.bill

import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBillItemsForAddressUseCase @Inject constructor(
    private val repository: BillRepository
) {

    operator fun invoke(address: String): Flow<List<BillItem>> {
        return repository.getBillItemsForAddress(address)
    }
}