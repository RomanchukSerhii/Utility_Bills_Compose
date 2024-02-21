package com.serhiiromanchuk.utilitybills.domain.repository

import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.model.BillWithUtilityServiceLists
import kotlinx.coroutines.flow.Flow

interface BillRepository {

    suspend fun insertBillItem(billItem: BillItem)

    suspend fun deleteBillItem(billItemId: Long)

    suspend fun updateBillAddress(address: String, billId: Long)

    suspend fun updateBillItems(billItems: List<BillItem>)

    fun getBillWithUtilityServices(): Flow<List<BillWithUtilityServiceLists>>

    fun getBillItemsForAddress(address: String): Flow<List<BillItem>>

    fun getBillItems(): Flow<List<BillItem>>
}