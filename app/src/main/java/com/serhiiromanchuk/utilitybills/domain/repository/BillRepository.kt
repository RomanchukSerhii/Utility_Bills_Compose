package com.serhiiromanchuk.utilitybills.domain.repository

import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import kotlinx.coroutines.flow.Flow

interface BillRepository {

    suspend fun insertBillItem(billItem: BillItem)

    suspend fun deleteBillItem(billItemId: Int)

    fun getBillItems(address: String): Flow<List<BillItem>>
}