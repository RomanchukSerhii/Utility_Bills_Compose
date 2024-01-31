package com.serhiiromanchuk.utilitybills.utils

import android.content.Context
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import com.serhiiromanchuk.utilitybills.R

// Formats a string with numbers separated by spaces in the thousands value
fun getUtilityMeterTransformedText(input: AnnotatedString): TransformedText {
    val out = input.text.digitWithSpace()

    val utilityMeterOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 3) return offset
            if (offset <= 6) return offset + 1
            if (offset <= 8) return offset + 2
            return 10
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 4) return offset
            if (offset <= 8) return offset - 1
            if (offset <= 10) return offset - 2
            return 8
        }

    }
    return TransformedText(AnnotatedString(out), utilityMeterOffsetTranslator)
}

fun getPriceTransformedText(input: AnnotatedString, context: Context): TransformedText {
    val currencySymbol = context.getString(R.string.currency_symbol)

    val out = input.text + currencySymbol

    val utilityMeterOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return offset
        }

        override fun transformedToOriginal(offset: Int): Int {
            return offset - 2
        }

    }
    return TransformedText(AnnotatedString(out), utilityMeterOffsetTranslator)
}

fun getCreditCardTransformedText(input: AnnotatedString, context: Context): TransformedText {
    // Making XXXX-XXXX-XXXX-XXXX string.
    val trimmed = if (input.text.length >= 16) input.text.substring(0..15) else input.text
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i % 4 == 3 && i != 15) out += "-"
    }

    /**
     * The offset translator should ignore the hyphen characters, so conversion from
     *  original offset to transformed text works like
     *  - The 4th char of the original text is 5th char in the transformed text.
     *  - The 13th char of the original text is 15th char in the transformed text.
     *  Similarly, the reverse conversion works like
     *  - The 5th char of the transformed text is 4th char in the original text.
     *  - The 12th char of the transformed text is 10th char in the original text.
     */
    val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 3) return offset
            if (offset <= 7) return offset + 1
            if (offset <= 11) return offset + 2
            if (offset <= 16) return offset + 3
            return 19
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 4) return offset
            if (offset <= 9) return offset - 1
            if (offset <= 14) return offset - 2
            if (offset <= 19) return offset - 3
            return 16
        }
    }

    return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
}