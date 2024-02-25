package com.serhiiromanchuk.utilitybills.presentation.core.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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

