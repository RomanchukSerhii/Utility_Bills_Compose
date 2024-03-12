package com.serhiiromanchuk.utilitybills.data.dbmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDate

@Entity(tableName = "bill_items")
@TypeConverters(LocalDateConverter::class)
data class BillDbModel (
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "package_creator_id") val packageCreatorId: Long,
    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "bill_description") val billDescription: String?
)