package com.serhiiromanchuk.utilitybills.domain.usecase.utilityservice

import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.domain.repository.UtilityServiceRepository
import javax.inject.Inject

class GetUtilityServiceUseCase @Inject constructor(
    private val repository: UtilityServiceRepository
) {

    suspend operator fun invoke(utilityServiceId: Int): UtilityServiceItem {
        return repository.getUtilityService(utilityServiceId)
    }
}