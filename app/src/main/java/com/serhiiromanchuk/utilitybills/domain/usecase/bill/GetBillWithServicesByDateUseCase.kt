package com.serhiiromanchuk.utilitybills.domain.usecase.bill

import com.serhiiromanchuk.utilitybills.domain.model.BillWithUtilityServices
import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository
import java.time.LocalDate
import javax.inject.Inject

class GetBillWithServicesByDateUseCase @Inject constructor(
    private val repository: BillRepository
) {

    suspend operator fun invoke(packageCreatorId: Long, date: LocalDate): BillWithUtilityServices? {
        return repository.getBillWithServicesByDate(packageCreatorId, date)
    }
}