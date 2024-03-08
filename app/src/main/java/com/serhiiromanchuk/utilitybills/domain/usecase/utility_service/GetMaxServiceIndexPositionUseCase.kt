package com.serhiiromanchuk.utilitybills.domain.usecase.utility_service

import com.serhiiromanchuk.utilitybills.domain.repository.UtilityServiceRepository
import javax.inject.Inject

class GetMaxServiceIndexPositionUseCase @Inject constructor(
    private val repository: UtilityServiceRepository
){

    suspend operator fun invoke(billCreatorId: Long): Int? {
        return repository.getMaxServiceIndexPosition(billCreatorId)
    }
}