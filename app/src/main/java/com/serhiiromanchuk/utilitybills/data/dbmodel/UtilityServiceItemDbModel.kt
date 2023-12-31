package com.serhiiromanchuk.utilitybills.data.dbmodel

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.serhiiromanchuk.utilitybills.domain.model.MeasurementUnit
import java.time.Month
import java.time.Year

@Entity(tableName = "utility_services")
data class UtilityServiceItemDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "year") val year: Int,
    @ColumnInfo(name = "month") val month: Month,
    @ColumnInfo(name = "tariff") val tariff: Double,
    @ColumnInfo(name = "is_meter_available") val isMeterAvailable: Boolean,
    @ColumnInfo(name = "previous_value") val previousValue: Int,
    @ColumnInfo(name = "current_value") val currentValue: Int,
    @ColumnInfo(name = "unit_of_measurement") val unitOfMeasurement: MeasurementUnit
)
