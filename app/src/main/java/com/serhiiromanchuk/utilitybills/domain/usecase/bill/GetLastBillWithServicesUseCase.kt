package com.serhiiromanchuk.utilitybills.domain.usecase.bill

import com.serhiiromanchuk.utilitybills.domain.model.BillWithUtilityServices
import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository
import javax.inject.Inject

class GetLastBillWithServicesUseCase @Inject constructor(
    private val repository: BillRepository
){

    suspend operator fun invoke(packageCreatorId: Long): BillWithUtilityServices? {
        return repository.getLastBillWithServices(packageCreatorId)
    }
}