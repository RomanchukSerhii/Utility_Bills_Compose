package com.serhiiromanchuk.utilitybills.data.dbmodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "utility_services")
data class UtilityServiceItemDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val tariff: Double,
    val isMeterAvailable: Boolean,
    val previousValue: Int,
    val currentValue: Int
)
