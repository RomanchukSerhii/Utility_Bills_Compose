package com.serhiiromanchuk.utilitybills.utils

fun String.getFormattedDigitsOnly(maxLength: Int): String {
    var result = ""
    for (char in this) {
        if (char.isDigit()) result += char
    }

    return if (result.length > 1 && result.first().digitToInt() == 0) {
        result.substring(1)
    } else if (result.length > maxLength) {
        result.takeLast(maxLength)
    } else result
}

fun String.digitWithSpace(): String {
    // Define the index from which we start the location of spaces
    val startIndex = this.length % 3
    val result = StringBuilder()

    // Add the first characters (before the first space)
    if (startIndex > 0) {
        result.append(this.substring(0, startIndex))
        if (startIndex < this.length) {
            result.append(" ")
        }
    }

    // Add the remaining numbers with spaces
    for (i in startIndex until this.length step 3) {
        result.append(this.substring(i, i + 3))
        if (i + 3 < this.length) {
            result.append(" ")
        }
    }

    return result.toString()
}