package com.serhiiromanchuk.utilitybills.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.serhiiromanchuk.utilitybills.data.dbmodel.BillPackageDbModel
import com.serhiiromanchuk.utilitybills.data.dbmodel.PackageWithBillsDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BillPackageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBillPackage(billPackage: BillPackageDbModel)

    @Query("DELETE FROM bill_packages WHERE id=:packageId")
    suspend fun deleteBillPackage(packageId: Long)

    @Update
    suspend fun updateBillPackages(billPackages: List<BillPackageDbModel>)

    @Query("SELECT MAX(index_position) FROM bill_packages")
    suspend fun getMaxIndexPosition(): Int?

    @Query("SELECT id FROM bill_packages ORDER BY id DESC LIMIT 1")
    suspend fun getLastBillPackageId(): Long?

    @Query("UPDATE bill_packages SET name=:packageName WHERE id=:packageId")
    suspend fun updatePackageName(packageName: String, packageId: Long)

    @Query("SELECT * FROM bill_packages ORDER BY index_position ASC")
    fun getBillPackages(): Flow<List<BillPackageDbModel>>

    @Transaction
    @Query("SELECT * FROM bill_packages WHERE id=:packageId")
    fun getBillPackageWithBills(packageId: Long): Flow<PackageWithBillsDbModel>
}