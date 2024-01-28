package com.serhiiromanchuk.utilitybills.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.utils.getFormattedDigitsOnly
import com.serhiiromanchuk.utilitybills.utils.getUtilityMeterTransformedText

@Composable
fun OutlinedTextFieldOnSurface(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String = "",
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            isError = isError,
            textStyle = MaterialTheme.typography.bodyLarge,
            label = {
                Text(
                    text = labelText,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            singleLine = singleLine,
            visualTransformation = visualTransformation
        )
    }
}

@Composable
fun UtilityMeterTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    label: String = "",
    imeAction: ImeAction = ImeAction.Next
) {

    // Set cursor to the end of string
    val direction = LocalLayoutDirection.current
//    var textFieldValue by remember {
//        mutableStateOf(
//            TextFieldValue(
//                text = value,
//                selection = if (direction == LayoutDirection.Ltr) TextRange(value.length) else TextRange.Zero
//            )
//        )
//    }

    // Create transparent color to cursor handle
    val transparentTextSelectionColors = TextSelectionColors(
        handleColor = Color.White.copy(alpha = 0f),
        backgroundColor = MaterialTheme.colorScheme.inversePrimary
    )

    // Clear focus if user press onDone Button
    var isFocused by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    // Set transparent color to cursor handle
    CompositionLocalProvider(
        LocalTextSelectionColors provides transparentTextSelectionColors
    ) {
        BasicTextField(
            modifier = modifier.onFocusChanged { isFocused = it.isFocused },
            value = TextFieldValue(
                text = value,
                selection = if (direction == LayoutDirection.Ltr) TextRange(value.length) else TextRange.Zero
            ),
            onValueChange = {
                val formattedValue = it.text.getFormattedDigitsOnly(8)
//                textFieldValue = it.copy(text = formattedValue)
                onValueChange(formattedValue)
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            visualTransformation = { input -> getUtilityMeterTransformedText(input) },
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            decorationBox = @Composable { innerTextField ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    LabelTextOnSurface(text = label)
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))
                    Row {
                        innerTextField()
                        Spacer(modifier = Modifier.weight(1f))
                        ErrorTrailingIcon(isError = isError)
                    }
                    TextFieldActiveIndicator(isEnabled = isFocused)
                }
            }
        )
    }
}

@Composable
fun ErrorTrailingIcon(
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    if (isError) {
        Icon(
            modifier = modifier.size(16.dp),
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = stringResource(R.string.input_error),
            tint = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun TextFieldActiveIndicator(
    isEnabled: Boolean = false
) {
    Box(
        modifier = Modifier
            .padding(top = dimensionResource(id = R.dimen.padding_extra_small))
            .height(if (isEnabled) 2.dp else 1.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSurfaceVariant)
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
        UtilityMeterTextField(
            value = "9624",
            onValueChange = {},
            label = "Попередні"
        )
    }
}