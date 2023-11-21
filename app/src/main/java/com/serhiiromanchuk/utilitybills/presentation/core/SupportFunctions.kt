package com.serhiiromanchuk.utilitybills.presentation.core

import android.os.Build
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun getSaveCardNumber(cardNumber: String): String {
    val separateNumbers = cardNumber.split(" ")
    return "${separateNumbers.first()} **** **** ${separateNumbers.last()}"
}

fun getCurrentMonth(): String {
    val currentDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now()
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    val formatter = DateTimeFormatter.ofPattern("LLLL", Locale("uk", "UA"))
    val currentMonth = currentDate.format(formatter)
    return currentMonth.capitalizeFirstChar()
}

fun String.capitalizeFirstChar() = this.replaceFirstChar {
    if (it.isLowerCase()) {
        it.titlecase(Locale.getDefault())
    } else it.toString()
}

