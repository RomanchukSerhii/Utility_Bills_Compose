package com.serhiiromanchuk.utilitybills.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private val calendar = Calendar.getInstance()
fun getCurrentMonth(): String {
    return SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar)
}

fun getCurrentYear(): String {
    return calendar.get(Calendar.YEAR).toString()
}