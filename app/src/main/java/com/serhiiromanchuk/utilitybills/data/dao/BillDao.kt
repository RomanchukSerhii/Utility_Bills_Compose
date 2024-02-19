package com.serhiiromanchuk.utilitybills.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.serhiiromanchuk.utilitybills.data.dbmodel.BillItemDbModel
import com.serhiiromanchuk.utilitybills.data.dbmodel.BillWithUtilityServiceListsDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBillItem(billItemDbModel: BillItemDbModel)

    @Query("DELETE FROM bill_items WHERE id=:billItemId")
    suspend fun deleteBillItem(billItemId: Long)

    @Query("UPDATE bill_items SET address=:address WHERE id=:id")
    suspend fun updateAddress(address: String, id: Long)

    @Query("SELECT * FROM bill_items WHERE address=:address")
    fun getBillItemsForAddress(address: String): Flow<List<BillItemDbModel>>

    @Query("SELECT * FROM bill_items")
    fun getBillItems(): Flow<List<BillItemDbModel>>

    @Transaction
    @Query("SELECT * FROM bill_items")
    fun getBillWithUtilityServices(): Flow<List<BillWithUtilityServiceListsDbModel>>
}