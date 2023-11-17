package com.serhiiromanchuk.utilitybills.domain.usecase.utilityservice

import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.domain.repository.UtilityServiceRepository
import kotlinx.coroutines.flow.Flow

class GetUtilityServicesUseCase (
    private val repository: UtilityServiceRepository
) {

    operator fun invoke(address: String): Flow<List<UtilityServiceItem>> {
        return repository.getUtilityServices(address)
    }
}