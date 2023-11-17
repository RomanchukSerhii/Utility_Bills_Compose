package com.serhiiromanchuk.utilitybills.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.serhiiromanchuk.utilitybills.data.dbmodel.UtilityServiceItemDbModel
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import kotlinx.coroutines.flow.Flow

@Dao
interface UtilityServiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUtilityService(utilityService: UtilityServiceItemDbModel)

    @Query("DELETE FROM utility_services WHERE id=:utilityServiceId")
    suspend fun deleteUtilityService(utilityServiceId: Int)

    @Query("SELECT * FROM utility_services")
    fun getUtilityServices(): Flow<List<UtilityServiceItemDbModel>>

}