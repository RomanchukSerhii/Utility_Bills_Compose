package com.serhiiromanchuk.utilitybills.domain.repository

import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.model.BillWithUtilityServiceLists
import kotlinx.coroutines.flow.Flow

interface BillRepository {

    suspend fun insertBillItem(billItem: BillItem)

    suspend fun deleteBillItem(billItemId: Long)

    fun getBillWithUtilityServices(): Flow<List<BillWithUtilityServiceLists>>

    fun getBillItemsForAddress(address: String): Flow<List<BillItem>>

    fun getBillItems(): Flow<List<BillItem>>
}