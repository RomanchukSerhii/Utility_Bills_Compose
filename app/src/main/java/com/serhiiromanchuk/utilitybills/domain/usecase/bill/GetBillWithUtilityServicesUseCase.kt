package com.serhiiromanchuk.utilitybills.domain.usecase.bill

import com.serhiiromanchuk.utilitybills.domain.model.BillWithUtilityServiceLists
import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository
import javax.inject.Inject

class GetBillWithUtilityServicesUseCase @Inject constructor(
    private val repository: BillRepository
) {

    suspend operator fun invoke(): List<BillWithUtilityServiceLists> {
        return repository.getBillWithUtilityServices()
    }
}