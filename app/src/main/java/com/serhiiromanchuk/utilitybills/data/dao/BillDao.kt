package com.serhiiromanchuk.utilitybills.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.serhiiromanchuk.utilitybills.data.dbmodel.BillItemDbModel
import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBillItem(billItemDbModel: BillItemDbModel)

    @Query("DELETE FROM bill_items WHERE id=:billItemId")
    suspend fun deleteBillItem(billItemId: Int)

    @Query("SELECT * FROM bill_items WHERE address=:address")
    fun getBillItems(address: String): Flow<List<BillItemDbModel>>
}