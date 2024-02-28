package com.serhiiromanchuk.utilitybills.data.repositoryimpl

import com.serhiiromanchuk.utilitybills.data.dao.BillDao
import com.serhiiromanchuk.utilitybills.data.mapper.BillItemMapper
import com.serhiiromanchuk.utilitybills.domain.model.Bill
import com.serhiiromanchuk.utilitybills.domain.model.BillWithUtilityServices
import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BillRepositoryImpl @Inject constructor(
    private val billDao: BillDao,
    private val mapper: BillItemMapper
) : BillRepository {
    override suspend fun insertBillItem(billItem: Bill) {
        billDao.insertBillItem(mapper.mapEntityToDbModel(billItem))
    }

    override suspend fun deleteBillItem(billItemId: Long) {
        billDao.deleteBillItem(billItemId)
    }

    override suspend fun deleteBillsFromPackage(packageId: Long) {
        billDao.deleteBillsFromPackage(packageId)
    }

    override suspend fun updateBillAddress(address: String, billId: Long) {
        billDao.updateAddress(address, billId)
    }

    override suspend fun updateBillItems(billItems: List<Bill>) {

        billDao.updateBillItems(mapper.mapListEntityToListDbModel(billItems))
    }

    override fun getBillWithUtilityServices(billId: Long): Flow<BillWithUtilityServices> {
        return billDao.getBillWithUtilityServices(billId)
            .map { mapper.mapBillWithServicesDbModelToEntity(it) }
    }
}