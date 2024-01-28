package com.serhiiromanchuk.utilitybills.presentation.core.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.domain.model.MeasurementUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeasurementExposeDropdownMenuBox(
    modifier: Modifier = Modifier,
    onValueChange: (MeasurementUnit) -> Unit
) {

    val measurementUnits = arrayOf(
        MeasurementUnit.CUBIC_METER,
        MeasurementUnit.KILOWATT
    )

    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedUnit by rememberSaveable { mutableStateOf(MeasurementUnit.CUBIC_METER) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = Modifier.menuAnchor(),
            value = selectedUnit.title,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { /*TODO*/ }
        ) {
            measurementUnits.forEach { measurementUnit ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = measurementUnit.title,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        selectedUnit = measurementUnit
                        onValueChange(measurementUnit)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressExposeDropdownMenuBox(
    modifier: Modifier = Modifier,
    currentAddress: String,
    addressList: List<String>,
    onValueChange: (String) -> Unit
) {

    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedAddress by rememberSaveable { mutableStateOf(currentAddress) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        BasicTextField(
            modifier = Modifier.menuAnchor(),
            value = selectedAddress,
            onValueChange = {},
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary
            ),
            readOnly = true,
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = selectedAddress,
                    visualTransformation = VisualTransformation.None,
                    innerTextField = innerTextField,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    singleLine = true,
                    enabled = true,
                    interactionSource = MutableInteractionSource(),
                    contentPadding = PaddingValues(0.dp),
                    label = {
                        LabelTextOnPrimary(text = "Адреса")
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0f),
                        focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
                        focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        )
//        TextField(
//            modifier = Modifier.menuAnchor(),
//            value = selectedAddress,
//            onValueChange = {},
//            readOnly = true,
//            label = {
//                LabelTextOnPrimary(text = "Поточна адреса")
//            },
//            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
//            colors = TextFieldDefaults.colors(
//                focusedContainerColor = MaterialTheme.colorScheme.primary,
//                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
//                focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
//                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
//            )
//        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { /*TODO*/ }
        ) {
            addressList.forEach { address ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = address,
                            style = MaterialTheme.typography.bodyMedium,
//                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    onClick = {
                        selectedAddress = address
                        onValueChange(address)
                        expanded = false
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }
}

