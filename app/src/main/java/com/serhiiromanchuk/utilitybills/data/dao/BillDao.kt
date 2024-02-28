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

@Dao
interface BillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBillItem(billItemDbModel: BillDbModel)

    @Query("DELETE FROM bill_items WHERE id=:billItemId")
    suspend fun deleteBillItem(billItemId: Long)

    @Query("DELETE FROM bill_items WHERE package_creator_id=:packageId")
    suspend fun deleteBillsFromPackage(packageId: Long)

    @Query("UPDATE bill_items SET address=:address WHERE id=:id")
    suspend fun updateAddress(address: String, id: Long)

    @Update
    suspend fun updateBillItems(billItems: List<BillDbModel>)

    @Transaction
    @Query("SELECT * FROM bill_items WHERE id=:billId")
    fun getBillWithUtilityServices(billId: Long): Flow<BillWithUtilityServicesDbModel>
}