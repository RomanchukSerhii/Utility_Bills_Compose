package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.components.TextFieldWithoutPadding
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package.AddPackageUiEvent


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
    onEvent: (AddPackageUiEvent) -> Unit
) {
    TextFieldWithoutPadding(
        modifier = Modifier.weight(1f),
        value = value,
        onValueChange = {
            if (it.isDigitsOnly()) {
                onEvent(AddPackageUiEvent.HouseChanged(it))
            }
        },
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
    onEvent: (AddPackageUiEvent) -> Unit
) {
    TextFieldWithoutPadding(
        modifier = Modifier.weight(1f),
        value = value,
        onValueChange = { onEvent(AddPackageUiEvent.BuildingChanged(it)) },
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
    onEvent: (AddPackageUiEvent) -> Unit
) {
    TextFieldWithoutPadding(
        modifier = Modifier.weight(1f),
        value = value,
        onValueChange = {
            if (it.isDigitsOnly()) {
                onEvent(AddPackageUiEvent.ApartmentChanged(it))
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        label = stringResource(R.string.appartment)
    )
}