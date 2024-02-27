package com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.serhiiromanchuk.utilitybills.presentation.core.components.RoundCheckBox
import com.serhiiromanchuk.utilitybills.presentation.core.components.RoundCheckBoxDefaults
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation.BillGenerationUiEvent

@Composable
fun ServiceCheckBox(
    serviceId: Long,
    isChecked: Boolean,
    onEvent: (BillGenerationUiEvent) -> Unit
) {
    RoundCheckBox(
        isChecked = isChecked,
        onClick = {
            onEvent(BillGenerationUiEvent.CheckStateChanged(serviceId, !isChecked))
        },
        color = RoundCheckBoxDefaults.colors(
            selectedColor = MaterialTheme.colorScheme.tertiary,
            disabledSelectedColor = MaterialTheme.colorScheme.surface,
            borderColor = if (isChecked) {
                MaterialTheme.colorScheme.tertiary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
        )
    )
}
