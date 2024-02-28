package com.serhiiromanchuk.utilitybills.domain.repository

import com.serhiiromanchuk.utilitybills.domain.model.BillPackage
import com.serhiiromanchuk.utilitybills.domain.model.PackageWithBills
import kotlinx.coroutines.flow.Flow

interface BillPackageRepository {
    suspend fun insertBillPackage(billPackage: BillPackage)

    suspend fun deleteBillPackage(packageId: Long)

    suspend fun getMaxIndexPosition(): Int?

    suspend fun updateBillPackages(billPackages: List<BillPackage>)

    fun getBillPackages(): Flow<List<BillPackage>>

    fun getBillPackageWitBills(packageId: Long): Flow<PackageWithBills>
}