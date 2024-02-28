package com.serhiiromanchuk.utilitybills.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.serhiiromanchuk.utilitybills.data.dbmodel.BillPackageDbModel
import com.serhiiromanchuk.utilitybills.data.dbmodel.PackageWithBillsDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BillPackageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBillPackage(billPackage: BillPackageDbModel)

    @Query("DELETE FROM bill_packages WHERE id=:packageId")
    suspend fun deleteBillPackage(packageId: Long)

    @Query("SELECT MAX(index_position) FROM bill_packages")
    suspend fun getMaxIndexPosition(): Int?

    @Query("SELECT * FROM bill_packages ORDER BY index_position ASC")
    fun getBillPackages(): Flow<List<BillPackageDbModel>>

    @Transaction
    @Query("SELECT * FROM bill_packages WHERE id=:packageId")
    fun getBillPackageWithBills(packageId: Long): Flow<PackageWithBillsDbModel>
}