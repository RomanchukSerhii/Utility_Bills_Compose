package com.serhiiromanchuk.utilitybills.data.dbmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bill_items")
data class BillItemDbModel (
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "month") val month: String,
    @ColumnInfo(name = "year") val year: String,
    @ColumnInfo(name = "card_number") val cardNumber: String,
    @ColumnInfo(name = "bill_description") val billDescription: String
)