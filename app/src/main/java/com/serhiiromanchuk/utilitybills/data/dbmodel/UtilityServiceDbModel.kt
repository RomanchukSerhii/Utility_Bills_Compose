package com.serhiiromanchuk.utilitybills.data.dbmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.serhiiromanchuk.utilitybills.domain.model.MeasurementUnit

@Entity(tableName = "utility_services")
data class UtilityServiceDbModel(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "bill_creator_id") val billCreatorId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "tariff") val tariff: Double,
    @ColumnInfo(name = "is_meter_available") val isMeterAvailable: Boolean,
    @ColumnInfo(name = "previous_value") val previousValue: String,
    @ColumnInfo(name = "current_value") val currentValue: String,
    @ColumnInfo(name = "unit_of_measurement") val unitOfMeasurement: MeasurementUnit
)
