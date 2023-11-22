package com.serhiiromanchuk.utilitybills.data.dbmodel

import androidx.room.TypeConverter
import java.time.Month

class LocalDateConverter {

    @TypeConverter
    fun fromMonth(value: Month): String {
        return value.toString()
    }

    @TypeConverter
    fun toMonth(value: String): Month {
        return Month.valueOf(value)
    }
}