package com.serhiiromanchuk.utilitybills.data.dbmodel

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateConverter {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.format(formatter)
    }

    @TypeConverter
    fun toLocalDate(value: String): LocalDate {
        return LocalDate.parse(value, formatter)
    }
}