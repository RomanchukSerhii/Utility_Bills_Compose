package com.serhiiromanchuk.utilitybills.domain.repository

import com.serhiiromanchuk.utilitybills.domain.model.UtilityService

interface UtilityServiceRepository {

    suspend fun insertUtilityService(utilityService: UtilityService)

    suspend fun deleteUtilityService(utilityServiceId: Long)

    suspend fun deleteServiceFromBill(billId: Long, serviceId: Long)

    suspend fun getUtilityService(utilityServiceId: Long): UtilityService
}