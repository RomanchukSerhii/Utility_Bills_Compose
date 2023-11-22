package com.serhiiromanchuk.utilitybills.domain.usecase.utilityservice

import com.serhiiromanchuk.utilitybills.domain.repository.UtilityServiceRepository
import javax.inject.Inject

class DeleteUtilityServiceUseCase @Inject constructor(
    private val repository: UtilityServiceRepository
) {

    suspend operator fun invoke(utilityServiceId: Int) {
        repository.deleteUtilityService(utilityServiceId)
    }
}