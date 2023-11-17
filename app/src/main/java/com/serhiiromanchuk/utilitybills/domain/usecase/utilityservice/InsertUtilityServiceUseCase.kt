package com.serhiiromanchuk.utilitybills.domain.usecase.utilityservice

import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.domain.repository.UtilityServiceRepository

class InsertUtilityServiceUseCase (
    private val repository: UtilityServiceRepository
) {

    suspend operator fun invoke(utilityService: UtilityServiceItem) {
        repository.insertUtilityService(utilityService)
    }
}