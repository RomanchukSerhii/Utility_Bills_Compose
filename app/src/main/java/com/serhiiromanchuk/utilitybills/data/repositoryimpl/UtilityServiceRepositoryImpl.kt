package com.serhiiromanchuk.utilitybills.data.repositoryimpl

import com.serhiiromanchuk.utilitybills.data.dao.UtilityServiceDao
import com.serhiiromanchuk.utilitybills.data.mapper.UtilityServiceItemMapper
import com.serhiiromanchuk.utilitybills.domain.model.UtilityService
import com.serhiiromanchuk.utilitybills.domain.repository.UtilityServiceRepository
import javax.inject.Inject

class UtilityServiceRepositoryImpl @Inject constructor(
    private val utilityServiceDao: UtilityServiceDao,
    private val mapper: UtilityServiceItemMapper
) : UtilityServiceRepository {
    override suspend fun insertUtilityService(utilityService: UtilityService) {
        utilityServiceDao.insertUtilityService(mapper.mapEntityToDbModel(utilityService))
    }

    override suspend fun deleteUtilityService(utilityServiceId: Long) {
        utilityServiceDao.deleteUtilityService(utilityServiceId)
    }

    override suspend fun deleteServiceFromBill(billId: Long, serviceId: Long) {
        utilityServiceDao.deleteServiceFromBill(billId, serviceId)
    }

    override suspend fun getUtilityService(utilityServiceId: Long): UtilityService {
        return mapper.mapDbModelToEntity(utilityServiceDao.getUtilityService(utilityServiceId))
    }
}