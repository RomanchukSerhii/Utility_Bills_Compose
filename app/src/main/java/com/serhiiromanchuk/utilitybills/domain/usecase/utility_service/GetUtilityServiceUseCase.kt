package com.serhiiromanchuk.utilitybills.domain.usecase.utility_service

import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.domain.repository.UtilityServiceRepository
import javax.inject.Inject

class GetUtilityServiceUseCase @Inject constructor(
    private val repository: UtilityServiceRepository
) {

    suspend operator fun invoke(utilityServiceId: Long): UtilityServiceItem {
        return repository.getUtilityService(utilityServiceId)
    }
}