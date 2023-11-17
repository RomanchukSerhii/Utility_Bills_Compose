package com.serhiiromanchuk.utilitybills.data.dbmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem

@Entity(tableName = "bill_items")
data class BillItemDbModel (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "month") val month: String,
    @ColumnInfo(name = "year") val year: Int,
    @ColumnInfo(name = "card_number") val cardNumber: String,
    @ColumnInfo(name = "utility_services") val utilityServices: List<UtilityServiceItem>,
    @ColumnInfo(name = "bill_description") val billDescription: String
)