package com.serhiiromanchuk.utilitybills.data.repositoryimpl

import com.serhiiromanchuk.utilitybills.data.dao.BillDao
import com.serhiiromanchuk.utilitybills.data.mapper.BillItemMapper
import com.serhiiromanchuk.utilitybills.domain.model.Bill
import com.serhiiromanchuk.utilitybills.domain.model.BillWithUtilityServices
import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
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

    override suspend fun updateBillItems(billItems: List<Bill>) {
        billDao.updateBillItems(mapper.mapListEntityToListDbModel(billItems))
    }

    override suspend fun getBillCount(packageCreatorId: Long): Int {
        return billDao.getBillCount(packageCreatorId)
    }

    override suspend fun getBillWithServicesByDate(
        packageCreatorId: Long,
        date: LocalDate
    ): BillWithUtilityServices? {
        val dbModel = billDao.getBillWithServicesByDate(packageCreatorId, date)
        return dbModel?.let { mapper.mapBillWithServicesDbModelToEntity(dbModel) }
    }

    override suspend fun getLastBillWithServices(packageCreatorId: Long): BillWithUtilityServices? {
        val dbModel = billDao.getLastBillWithServices(packageCreatorId)
        return dbModel?.let { mapper.mapBillWithServicesDbModelToEntity(dbModel) }
    }

    override fun getBillWithServices(billId: Long): Flow<BillWithUtilityServices> {
        return billDao.getBillWithUtilityServices(billId)
            .map { mapper.mapBillWithServicesDbModelToEntity(it) }
    }
}