package com.serhiiromanchuk.utilitybills.domain.repository

import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem

interface UtilityServiceRepository {

    suspend fun insertUtilityService(utilityService: UtilityServiceItem)

    suspend fun deleteUtilityService(utilityServiceId: Int)

    suspend fun getUtilityService(utilityServiceId: Int): UtilityServiceItem
}