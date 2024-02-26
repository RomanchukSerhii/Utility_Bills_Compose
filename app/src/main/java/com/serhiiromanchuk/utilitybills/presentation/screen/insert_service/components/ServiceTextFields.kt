package com.serhiiromanchuk.utilitybills.presentation.screen.insert_service.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.components.TextFieldWithoutPadding
import com.serhiiromanchuk.utilitybills.presentation.screen.insert_service.InsertServiceUiEvent
import com.serhiiromanchuk.utilitybills.utils.getFormattedDigitsOnly
import com.serhiiromanchuk.utilitybills.utils.getPriceTransformedText
import com.serhiiromanchuk.utilitybills.utils.getUtilityMeterTransformedText
import com.serhiiromanchuk.utilitybills.utils.isPriceFormat
import com.serhiiromanchuk.utilitybills.utils.replaceComaToDot

@Composable
fun ServiceNameTextField(
    modifier: Modifier = Modifier,
    serviceName: String,
    onEvent: (InsertServiceUiEvent) -> Unit
) {
    TextFieldWithoutPadding(
        modifier = modifier,
        value = serviceName,
        onValueChange = { onEvent(InsertServiceUiEvent.NameChanged(it)) },
        label = stringResource(R.string.service_name),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next
        ),
        singleLine = true
    )
}

@Composable
fun TariffTextField(
    modifier: Modifier = Modifier,
    tariff: String,
    onEvent: (InsertServiceUiEvent) -> Unit
) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    TextFieldWithoutPadding(
        modifier = modifier,
        value = getTariffValue(isFocused, tariff),
        onValueChange = {
            if (it.isPriceFormat()) {
                onEvent(InsertServiceUiEvent.TariffChanged(it.replaceComaToDot()))
            }
        },
        label = stringResource(R.string.tariff_label),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        visualTransformation = { input -> getPriceTransformedText(input, context) },
        interactionSource = interactionSource
    )
}

@Composable
fun RowScope.PreviousValueTextField(
    previousValue: String,
    onEvent: (InsertServiceUiEvent) -> Unit
) {
    TextFieldWithoutPadding(
        modifier = Modifier.weight(1f),
        value = previousValue,
        onValueChange = {
            val formattedValue = it.getFormattedDigitsOnly(8)
            onEvent(InsertServiceUiEvent.PreviousValueChanged(formattedValue))
        },
        label = stringResource(id = R.string.previous_value),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        visualTransformation = { input -> getUtilityMeterTransformedText(input) },
    )
}

@Composable
fun RowScope.CurrentValueTextField(
    currentValue: String,
    onEvent: (InsertServiceUiEvent) -> Unit
) {
    TextFieldWithoutPadding(
        modifier = Modifier.weight(1f),
        value = currentValue,
        onValueChange = {
            val formattedValue = it.getFormattedDigitsOnly(8)
            onEvent(InsertServiceUiEvent.CurrentValueChanged(formattedValue))
        },
        label = stringResource(id = R.string.current_value),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        visualTransformation = { input -> getUtilityMeterTransformedText(input) },
    )
}

private fun getTariffValue(isFocused: Boolean, value: String): String {
    return if (isFocused && value == "0") ""
    else if (!isFocused && value == "") "0"
    else value
}