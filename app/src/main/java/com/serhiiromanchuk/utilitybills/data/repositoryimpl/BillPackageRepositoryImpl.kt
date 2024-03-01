package com.serhiiromanchuk.utilitybills.data.repositoryimpl

import com.serhiiromanchuk.utilitybills.data.dao.BillPackageDao
import com.serhiiromanchuk.utilitybills.data.mapper.BillPackageMapper
import com.serhiiromanchuk.utilitybills.domain.model.BillPackage
import com.serhiiromanchuk.utilitybills.domain.model.PackageWithBills
import com.serhiiromanchuk.utilitybills.domain.repository.BillPackageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BillPackageRepositoryImpl @Inject constructor(
    private val billPackageDao: BillPackageDao,
    private val mapper: BillPackageMapper
) : BillPackageRepository {
    override suspend fun insertBillPackage(billPackage: BillPackage) {
        billPackageDao.insertBillPackage(mapper.mapEntityToDbModel(billPackage))
    }

    override suspend fun deleteBillPackage(packageId: Long) {
        billPackageDao.deleteBillPackage(packageId)
    }

    override suspend fun getMaxIndexPosition(): Int? {
        return billPackageDao.getMaxIndexPosition()
    }

    override suspend fun getLastBillPackageId(): Long? {
        return billPackageDao.getLastBillPackageId()
    }

    override suspend fun updatePackageName(packageName: String, packageId: Long) {
        billPackageDao.updatePackageName(packageName, packageId)
    }

    override suspend fun updateBillPackages(billPackages: List<BillPackage>) {
        billPackageDao.updateBillPackages(mapper.mapListEntityToListDbModel(billPackages))
    }

    override fun getBillPackages(): Flow<List<BillPackage>> {
        return billPackageDao.getBillPackages().map { mapper.mapListDbModelToListEntity(it) }
    }

    override fun getBillPackageWitBills(packageId: Long): Flow<PackageWithBills> {
        return billPackageDao.getBillPackageWithBills(packageId).map {
            mapper.mapPackageWithBillDbModelToEntity(it)
        }
    }
}