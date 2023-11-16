package com.serhiiromanchuk.utilitybills.domain.usecase

import com.serhiiromanchuk.utilitybills.domain.repository.UtilityServiceRepository

class DeleteUtilityServiceUseCase (
    private val repository: UtilityServiceRepository
) {

    suspend operator fun invoke(utilityServiceId: Int) {
        repository.deleteUtilityService(utilityServiceId)
    }
}