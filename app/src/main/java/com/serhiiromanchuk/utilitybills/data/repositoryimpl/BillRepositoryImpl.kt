package com.serhiiromanchuk.utilitybills.data.repositoryimpl

import com.serhiiromanchuk.utilitybills.data.dao.BillDao
import com.serhiiromanchuk.utilitybills.data.mapper.BillItemMapper
import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BillRepositoryImpl (
    private val billDao: BillDao,
    private val mapper: BillItemMapper
) : BillRepository {
    override suspend fun insertBillItem(billItem: BillItem) {
        billDao.insertBillItem(mapper.mapEntityToDbModel(billItem))
    }

    override suspend fun deleteBillItem(billItemId: Int) {
        billDao.deleteBillItem(billItemId)
    }

    override fun getBillItems(address: String): Flow<List<BillItem>> {
        return billDao.getBillItems(address).map { mapper.mapListDbModelToListEntity(it) }
    }

}