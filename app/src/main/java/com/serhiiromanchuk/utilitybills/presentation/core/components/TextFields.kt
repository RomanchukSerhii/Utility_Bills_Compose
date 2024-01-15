package com.serhiiromanchuk.utilitybills.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.utils.digitWithSpace
import com.serhiiromanchuk.utilitybills.utils.getFormattedDigitsOnly

@Composable
fun OutlinedTextFieldOnSurface(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String = "",
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
) {
    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = keyboardOptions,
            isError = isError,
            textStyle = MaterialTheme.typography.bodyLarge,
            label = {
                Text(
                    text = labelText,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )
    }
}

@Composable
fun TextFieldOnSurface(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    label: String = ""
) {
    var isFocused by rememberSaveable { mutableStateOf(false) }
    BasicTextField(
        modifier = modifier.onFocusChanged { isFocused = it.isFocused },
        value = value,
        onValueChange = {
            onValueChange(it.getFormattedDigitsOnly(8))
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        visualTransformation = { input ->
            val out = input.text.digitWithSpace()
            val testOffsetTranslator = object : OffsetMapping {
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
            TransformedText(AnnotatedString(out), testOffsetTranslator)
        },
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
        decorationBox = @Composable { innerTextField ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                LabelTextOnSurface(text = label)
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))
                innerTextField()
                Box(
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.padding_extra_small))
                        .height(if (isFocused) 2.dp else 1.dp)
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }
        }
    )
}

@DarkLightPreviews
@Composable
private fun TextFieldsPreview() {
    UtilityBillsTheme {
        OutlinedTextFieldOnSurface(value = "", onValueChange = {}, labelText = "Test:")
    }
}

@DarkLightPreviews
@Composable
private fun TextFieldOnSurfacePreview() {
    UtilityBillsTheme {
        TextFieldOnSurface(
            value = "9624",
            onValueChange = {},
            label = "Попередні"
        )
    }
}