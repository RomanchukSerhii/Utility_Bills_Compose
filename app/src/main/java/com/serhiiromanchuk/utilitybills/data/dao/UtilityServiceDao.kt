package com.serhiiromanchuk.utilitybills.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.serhiiromanchuk.utilitybills.data.dbmodel.UtilityServiceDbModel

@Dao
interface UtilityServiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUtilityService(utilityService: UtilityServiceDbModel)

    @Query("DELETE FROM utility_services WHERE id=:utilityServiceId")
    suspend fun deleteUtilityService(utilityServiceId: Long)

    @Query("DELETE FROM utility_services WHERE bill_creator_id=:billId AND id=:serviceId")
    suspend fun deleteServiceFromBill(billId: Long, serviceId: Long)

    @Query("SELECT * FROM utility_services WHERE id=:utilityServiceId")
    suspend fun getUtilityService(utilityServiceId: Long): UtilityServiceDbModel

}