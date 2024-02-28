package com.serhiiromanchuk.utilitybills.data.dbmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bill_items")
data class BillDbModel (
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "package_creator_id") val packageCreatorId: Long,
    @ColumnInfo(name = "payer_name") val payerName: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "bill_description") val billDescription: String
)