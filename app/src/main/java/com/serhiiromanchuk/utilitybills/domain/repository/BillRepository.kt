package com.serhiiromanchuk.utilitybills.domain.repository

import com.serhiiromanchuk.utilitybills.domain.model.Bill
import com.serhiiromanchuk.utilitybills.domain.model.BillWithUtilityServices
import kotlinx.coroutines.flow.Flow

interface BillRepository {

    suspend fun insertBillItem(billItem: Bill)

    suspend fun deleteBillItem(billItemId: Long)

    suspend fun deleteBillsFromPackage(packageId: Long)

    suspend fun updateBillItems(billItems: List<Bill>)

    suspend fun getBillCount(packageCreatorId: Long): Int

    suspend fun getBillWithServicesByDate(packageCreatorId: Long, date: String): BillWithUtilityServices?

    suspend fun getLastBillWithServices(packageCreatorId: Long): BillWithUtilityServices?

    fun getBillWithServices(billId: Long): Flow<BillWithUtilityServices>
}