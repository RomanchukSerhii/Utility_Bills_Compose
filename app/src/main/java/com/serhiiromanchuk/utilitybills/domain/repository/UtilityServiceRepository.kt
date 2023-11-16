package com.serhiiromanchuk.utilitybills.domain.repository

import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import kotlinx.coroutines.flow.Flow

interface UtilityServiceRepository {

    suspend fun insertUtilityService(utilityService: UtilityServiceItem)

    suspend fun deleteUtilityService(utilityServiceId: Int)

    fun getUtilityServices(): Flow<List<UtilityServiceItem>>
}