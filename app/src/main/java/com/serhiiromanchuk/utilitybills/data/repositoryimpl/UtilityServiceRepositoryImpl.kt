package com.serhiiromanchuk.utilitybills.data.repositoryimpl

import com.serhiiromanchuk.utilitybills.data.dao.UtilityServiceDao
import com.serhiiromanchuk.utilitybills.data.mapper.UtilityServiceItemMapper
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.domain.repository.UtilityServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UtilityServiceRepositoryImpl(
    private val utilityServiceDao: UtilityServiceDao,
    private val mapper: UtilityServiceItemMapper
) : UtilityServiceRepository {
    override suspend fun insertUtilityService(utilityService: UtilityServiceItem) {
        utilityServiceDao.insertUtilityService(mapper.mapEntityToDbModel(utilityService))
    }

    override suspend fun deleteUtilityService(utilityServiceId: Int) {
        utilityServiceDao.deleteUtilityService(utilityServiceId)
    }

    override fun getUtilityServices(): Flow<List<UtilityServiceItem>> {
        return utilityServiceDao.getUtilityServices().map {
            mapper.mapListDbModelToListEntity(it)
        }
    }
}