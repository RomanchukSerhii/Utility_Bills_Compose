package com.serhiiromanchuk.utilitybills.presentation.screen.insert_service.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.MeasurementExposeDropdownMenuBox
import com.serhiiromanchuk.utilitybills.presentation.core.components.TitleTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.screen.insert_service.InsertServiceUiEvent
import com.serhiiromanchuk.utilitybills.presentation.screen.insert_service.InsertServiceUiState
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme

@Composable
fun InsertServiceForm(
    modifier: Modifier = Modifier,
    screenState: InsertServiceUiState,
    onEvent: (InsertServiceUiEvent) -> Unit
) {
    Column(modifier = modifier) {
        TitleTextOnSurface(
            modifier = Modifier.padding(vertical = 8.dp),
            text = stringResource(R.string.enter_utility_service_info)
        )

        Row {
            ServiceNameTextField(
                modifier = Modifier.weight(1f),
                serviceName = screenState.serviceName,
                onEvent = onEvent
            )

            Spacer(modifier = Modifier.width(12.dp))

            TariffTextField(
                modifier = Modifier.width(80.dp),
                tariff = screenState.tariff, onEvent = onEvent
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        MeterCheckBox(isMeterAvailable = screenState.isMeterAvailable, onEvent = onEvent)

        if (screenState.isMeterAvailable) {
            Row {
                PreviousValueTextField(
                    previousValue = screenState.previousValue,
                    onEvent = onEvent
                )

                Spacer(modifier = Modifier.width(12.dp))

                CurrentValueTextField(
                    currentValue = screenState.currentValue,
                    onEvent = onEvent
                )

                Spacer(modifier = Modifier.width(12.dp))

                MeasurementExposeDropdownMenuBox(
                    modifier = Modifier.width(80.dp),
                    onValueChange = { onEvent(InsertServiceUiEvent.UnitOfMeasurementChanged(it)) }
                )
            }
        }
    }
}


@DarkLightPreviews
@Composable
private fun InsertServiceFormPreview() {
    UtilityBillsTheme {
        InsertServiceForm(
            modifier = Modifier.padding(16.dp),
            screenState = InsertServiceUiState(),
            onEvent = {}
        )
    }
}