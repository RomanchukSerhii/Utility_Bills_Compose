package com.serhiiromanchuk.utilitybills.domain.usecase.utility_service

import com.serhiiromanchuk.utilitybills.domain.repository.UtilityServiceRepository
import javax.inject.Inject

class DeleteUtilityServiceUseCase @Inject constructor(
    private val repository: UtilityServiceRepository
) {

    suspend operator fun invoke(utilityServiceId: Long) {
        repository.deleteUtilityService(utilityServiceId)
    }
}