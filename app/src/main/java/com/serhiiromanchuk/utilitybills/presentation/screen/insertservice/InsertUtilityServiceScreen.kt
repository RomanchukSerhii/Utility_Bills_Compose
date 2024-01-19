package com.serhiiromanchuk.utilitybills.presentation.screen.insertservice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.model.MeasurementUnit
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.MeasurementExposeDropdownMenuBox
import com.serhiiromanchuk.utilitybills.presentation.core.components.OutlinedTextFieldOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.PrimaryButton
import com.serhiiromanchuk.utilitybills.presentation.core.components.RoundCheckBox
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme


@Composable
fun InsertUtilityServiceScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: InsertUtilityServiceViewModel = viewModel()
    val screenState by viewModel.screenState.collectAsState()

    InsertUtilityServiceContent(
        modifier = modifier,
        screenState = screenState,
        onNameChanged = viewModel::onNameChanged,
        onTariffChanged = viewModel::onTariffChanged,
        onMeterAvailableChanged = viewModel::onMeterAvailableChanged,
        onPreviousValueChanged = viewModel::onPreviousValueChanged,
        onMeasurementUnitChanged = viewModel::onMeasurementUnitChanged
    )
}

@Composable
fun InsertUtilityServiceContent(
    modifier: Modifier = Modifier,
    screenState: InsertUtilityServiceScreenState,
    onNameChanged: (String) -> Unit,
    onTariffChanged: (String) -> Unit,
    onMeterAvailableChanged: (Boolean) -> Unit,
    onPreviousValueChanged: (String) -> Unit,
    onMeasurementUnitChanged: (MeasurementUnit) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextFieldOnSurface(
            value = screenState.name,
            onValueChange = onNameChanged,
            isError = screenState.isNameEmpty,
            labelText = "Назва послуги:"
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))

        OutlinedTextFieldOnSurface(
            value = screenState.tariff,
            onValueChange = onTariffChanged,
            isError = (screenState.isTariffEmpty || screenState.isTariffNotDouble),
            labelText = "Тариф:"
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundCheckBox(
                isChecked = screenState.isMeterAvailable,
                onClick = onMeterAvailableChanged
            )
            Text(
                text = "Наявність лічильника",
                style = MaterialTheme.typography.titleMedium
            )
        }

        if (screenState.isMeterAvailable) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                OutlinedTextFieldOnSurface(
                    modifier = Modifier.weight(1f),
                    value = screenState.previousValue,
                    onValueChange = onPreviousValueChanged,
                    isError = (screenState.isPreviousValueEmpty || screenState.isPreviousValueNotDigit),
                    labelText = "Попередні значення:"
                )

                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.width_medium)))

                MeasurementExposeDropdownMenuBox(
                    modifier = Modifier.width(100.dp),
                    onValueChange = onMeasurementUnitChanged
                )
            }


        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_large)))



        PrimaryButton(
            text = "Зберегти",
            onClick = { }
        )
    }
}

@DarkLightPreviews
@Composable
fun InsertUtilityServiceContentPreview() {

    UtilityBillsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            InsertUtilityServiceContent(
                modifier = Modifier.padding(16.dp),
                screenState = InsertUtilityServiceScreenState(isMeterAvailable = true),
                onNameChanged = {},
                onTariffChanged = {},
                onMeterAvailableChanged = {},
                onPreviousValueChanged = {},
                onMeasurementUnitChanged = {}
            )
        }
    }
}

