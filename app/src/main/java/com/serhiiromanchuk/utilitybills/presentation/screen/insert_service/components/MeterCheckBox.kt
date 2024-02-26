package com.serhiiromanchuk.utilitybills.presentation.screen.insert_service.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.components.RoundCheckBox
import com.serhiiromanchuk.utilitybills.presentation.core.components.RoundCheckBoxDefaults
import com.serhiiromanchuk.utilitybills.presentation.screen.insert_service.InsertServiceUiEvent

@Composable
fun MeterCheckBox(
    isMeterAvailable: Boolean,
    onEvent: (InsertServiceUiEvent) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundCheckBox(
            isChecked = isMeterAvailable,
            onClick = { onEvent(InsertServiceUiEvent.MeterAvailableChanged(it)) },
            color = RoundCheckBoxDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.tertiary,
                disabledSelectedColor = MaterialTheme.colorScheme.surface,
                borderColor = if (isMeterAvailable) {
                    MaterialTheme.colorScheme.tertiary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        )
        Text(
            text = stringResource(R.string.meter_available),
            style = MaterialTheme.typography.titleMedium
        )
    }
}