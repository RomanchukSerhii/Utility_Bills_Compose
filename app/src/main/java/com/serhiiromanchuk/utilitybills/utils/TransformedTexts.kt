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