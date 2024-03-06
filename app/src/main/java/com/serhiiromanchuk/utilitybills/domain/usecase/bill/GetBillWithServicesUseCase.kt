package com.serhiiromanchuk.utilitybills.domain.usecase.bill

import com.serhiiromanchuk.utilitybills.domain.model.BillWithUtilityServices
import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBillWithServicesUseCase @Inject constructor(
    private val repository: BillRepository
) {

    operator fun invoke(billId: Long): Flow<BillWithUtilityServices> {
        return repository.getBillWithServices(billId)
    }
}