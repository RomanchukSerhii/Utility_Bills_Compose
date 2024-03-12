package com.serhiiromanchuk.utilitybills.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.serhiiromanchuk.utilitybills.data.dbmodel.BillDbModel
import com.serhiiromanchuk.utilitybills.data.dbmodel.BillWithUtilityServicesDbModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface BillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBillItem(billItemDbModel: BillDbModel)

    @Query("DELETE FROM bill_items WHERE id=:billItemId")
    suspend fun deleteBillItem(billItemId: Long)

    @Query("DELETE FROM bill_items WHERE package_creator_id=:packageId")
    suspend fun deleteBillsFromPackage(packageId: Long)

    @Update
    suspend fun updateBillItems(billItems: List<BillDbModel>)

    @Query("SELECT COUNT(*) FROM bill_items WHERE package_creator_id=:packageCreatorId")
    suspend fun getBillCount(packageCreatorId: Long): Int

    @Transaction
    @Query("SELECT * FROM bill_items WHERE package_creator_id=:packageCreatorId AND date=:date")
    suspend fun getBillWithServicesByDate(packageCreatorId: Long, date: LocalDate): BillWithUtilityServicesDbModel?

    @Transaction
    @Query("SELECT * FROM bill_items WHERE package_creator_id=:packageCreatorId ORDER BY date DESC LIMIT 1")
    suspend fun getLastBillWithServices(packageCreatorId: Long): BillWithUtilityServicesDbModel?


    @Transaction
    @Query("SELECT * FROM bill_items WHERE id=:billId")
    fun getBillWithUtilityServices(billId: Long): Flow<BillWithUtilityServicesDbModel>
}