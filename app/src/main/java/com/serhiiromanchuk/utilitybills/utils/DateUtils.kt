package com.serhiiromanchuk.utilitybills.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private val calendar = Calendar.getInstance()
fun getCurrentMonth(): String {
    return SimpleDateFormat("LLLL", Locale.getDefault())
        .format(calendar.time)
        .replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
}

fun getCurrentYear(): String {
    return calendar.get(Calendar.YEAR).toString()
}

fun getCurrentDate(): String {
    return "${getCurrentMonth()} ${getCurrentYear()}"
}