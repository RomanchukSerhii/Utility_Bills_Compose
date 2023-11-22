package com.serhiiromanchuk.utilitybills.data.repositoryimpl

import com.serhiiromanchuk.utilitybills.data.dao.UtilityServiceDao
import com.serhiiromanchuk.utilitybills.data.mapper.UtilityServiceItemMapper
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.domain.repository.UtilityServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UtilityServiceRepositoryImpl @Inject constructor(
    private val utilityServiceDao: UtilityServiceDao,
    private val mapper: UtilityServiceItemMapper
) : UtilityServiceRepository {
    override suspend fun insertUtilityService(utilityService: UtilityServiceItem) {
        utilityServiceDao.insertUtilityService(mapper.mapEntityToDbModel(utilityService))
    }

    override suspend fun deleteUtilityService(utilityServiceId: Int) {
        utilityServiceDao.deleteUtilityService(utilityServiceId)
    }

    override suspend fun getUtilityService(utilityServiceId: Int): UtilityServiceItem {
        return mapper.mapDbModelToEntity(utilityServiceDao.getUtilityService(utilityServiceId))
    }

    override fun getUtilityServices(address: String): Flow<List<UtilityServiceItem>> {
        return utilityServiceDao.getUtilityServices(address).map {
            mapper.mapListDbModelToListEntity(it)
        }
    }
}