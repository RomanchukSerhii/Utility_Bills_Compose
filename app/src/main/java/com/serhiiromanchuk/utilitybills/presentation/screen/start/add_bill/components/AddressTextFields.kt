package com.serhiiromanchuk.utilitybills.presentation.screen.start.add_bill.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.components.TextFieldWithoutPadding
import com.serhiiromanchuk.utilitybills.presentation.screen.start.add_bill.AddBillScreenEvent


@Composable
fun AddressTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = ""
) {
    TextFieldWithoutPadding(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onValueChange(it) },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        label = label
    )
}

@Composable
fun RowScope.HouseTextField(
    value: String,
    onEvent: (AddBillScreenEvent) -> Unit
) {
    TextFieldWithoutPadding(
        modifier = Modifier.weight(1f),
        value = value,
        onValueChange = {
            if (it.isDigitsOnly()) {
                onEvent(AddBillScreenEvent.HouseChanged(it))
            }
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        label = stringResource(R.string.house)
    )
}

@Composable
fun RowScope.BuildingTextField(
    value: String,
    onEvent: (AddBillScreenEvent) -> Unit
) {
    TextFieldWithoutPadding(
        modifier = Modifier.weight(1f),
        value = value,
        onValueChange = { onEvent(AddBillScreenEvent.BuildingChanged(it)) },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        label = stringResource(R.string.building)
    )
}

@Composable
fun RowScope.ApartmentTextField(
    value: String,
    onEvent: (AddBillScreenEvent) -> Unit
) {
    TextFieldWithoutPadding(
        modifier = Modifier.weight(1f),
        value = value,
        onValueChange = {
            if (it.isDigitsOnly()) {
                onEvent(AddBillScreenEvent.ApartmentChanged(it))
            }
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        label = stringResource(R.string.appartment)
    )
}