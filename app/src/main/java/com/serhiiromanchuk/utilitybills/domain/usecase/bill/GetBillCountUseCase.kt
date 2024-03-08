package com.serhiiromanchuk.utilitybills.domain.usecase.bill

import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository
import javax.inject.Inject

class GetBillCountUseCase @Inject constructor(
    private val repository: BillRepository
) {

    suspend operator fun invoke(packageCreatorId: Long): Int {
        return repository.getBillCount(packageCreatorId)
    }
}