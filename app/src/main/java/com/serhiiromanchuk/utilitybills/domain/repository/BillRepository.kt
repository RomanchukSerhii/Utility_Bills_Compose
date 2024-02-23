package com.serhiiromanchuk.utilitybills.domain.repository

import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.model.BillWithUtilityServices
import kotlinx.coroutines.flow.Flow

interface BillRepository {

    suspend fun insertBillItem(billItem: BillItem)

    suspend fun deleteBillItem(billItemId: Long)

    suspend fun updateBillAddress(address: String, billId: Long)

    suspend fun updateBillItems(billItems: List<BillItem>)

    suspend fun getMaxItemPosition(): Int?

    fun getBillWithUtilityServices(billId: Long): Flow<BillWithUtilityServices>

    fun getBillItemsForAddress(address: String): Flow<List<BillItem>>

    fun getBillItems(): Flow<List<BillItem>>
}