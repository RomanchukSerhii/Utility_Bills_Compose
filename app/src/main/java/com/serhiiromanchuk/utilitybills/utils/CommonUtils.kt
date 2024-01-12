package com.serhiiromanchuk.utilitybills.utils

fun getMaskingCardNumber(cardNumber: String): String {
    val separateNumbers = cardNumber.split(" ")
    return "${separateNumbers.first()} **** **** ${separateNumbers.last()}"
}

fun formatToCardNumberType(input: String): String {
    val numbers = input.toCharArray().filter { it.isDigit() }
    val result = StringBuilder()
    numbers.forEachIndexed { index, number ->
        if (index == 16) return result.toString()
        if (index % 4 == 0) {
            result.append(" $number")
        } else {
            result.append(number)
        }
    }
    return result.toString().trimStart()
}

