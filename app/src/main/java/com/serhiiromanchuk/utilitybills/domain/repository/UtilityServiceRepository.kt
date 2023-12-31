package com.serhiiromanchuk.utilitybills.domain.repository

import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import kotlinx.coroutines.flow.Flow

interface UtilityServiceRepository {

    suspend fun insertUtilityService(utilityService: UtilityServiceItem)

    suspend fun deleteUtilityService(utilityServiceId: Int)

    suspend fun getUtilityService(utilityServiceId: Int): UtilityServiceItem

    fun getUtilityServices(address: String): Flow<List<UtilityServiceItem>>
}