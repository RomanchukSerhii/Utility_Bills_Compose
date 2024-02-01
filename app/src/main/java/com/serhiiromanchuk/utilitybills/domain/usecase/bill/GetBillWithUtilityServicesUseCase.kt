package com.serhiiromanchuk.utilitybills.domain.usecase.bill

import com.serhiiromanchuk.utilitybills.domain.model.BillWithUtilityServiceLists
import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBillWithUtilityServicesUseCase @Inject constructor(
    private val repository: BillRepository
) {

    operator fun invoke(): Flow<List<BillWithUtilityServiceLists>> {
        return repository.getBillWithUtilityServices()
    }
}