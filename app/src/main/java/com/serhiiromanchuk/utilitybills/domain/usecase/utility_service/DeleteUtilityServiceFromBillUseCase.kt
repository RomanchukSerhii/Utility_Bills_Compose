package com.serhiiromanchuk.utilitybills.domain.usecase.utility_service

import com.serhiiromanchuk.utilitybills.domain.repository.UtilityServiceRepository
import javax.inject.Inject

class DeleteUtilityServiceFromBillUseCase @Inject constructor(
    private val repository: UtilityServiceRepository
){
    suspend operator fun invoke(billId: Long, serviceId: Long) {
        repository.deleteServiceFromBill(billId, serviceId)
    }
}