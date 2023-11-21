package com.serhiiromanchuk.utilitybills.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.model.MeasurementUnit
import com.serhiiromanchuk.utilitybills.presentation.core.components.CustomOutlinedTextField
import com.serhiiromanchuk.utilitybills.presentation.core.components.MeasurementExposeDropdownMenuBox
import com.serhiiromanchuk.utilitybills.presentation.core.components.PrimaryButton
import com.serhiiromanchuk.utilitybills.presentation.screen.InsertUtilityServiceScreenUiState.Initial
import com.serhiiromanchuk.utilitybills.presentation.screen.InsertUtilityServiceScreenUiState.Content
import com.serhiiromanchuk.utilitybills.presentation.screen.InsertUtilityServiceScreenUiState.Error

@Composable
fun InsertUtilityServiceScreen(
    modifier: Modifier = Modifier,
    screenUiState: InsertUtilityServiceScreenUiState,
    onSaveButtonClick: (
        name: String,
        tariff: String,
        isMeterAvailable: Boolean,
        previousValue: String,
        unitOfMeasurement: MeasurementUnit
    ) -> Unit
) {

    var name by remember { mutableStateOf("") }
    var tariff by remember { mutableStateOf("") }
    var isMeterAvailable by remember { mutableStateOf(true) }
    var previousValue by remember { mutableStateOf("0") }
    var unitOfMeasurement by remember { mutableStateOf(MeasurementUnit.CUBIC_METER) }

    var isNameEmpty by remember { mutableStateOf(false) }
    var isTariffEmpty by remember { mutableStateOf(false) }
    var isTariffNotDouble by remember { mutableStateOf(false) }
    var isPreviousValueEmpty by remember { mutableStateOf(false) }
    var isPreviousValueNotDigit by remember { mutableStateOf(false) }

    when (screenUiState) {
        is Initial -> {
            name = screenUiState.name
            tariff = screenUiState.tariff
            isMeterAvailable = screenUiState.isMeterAvailable
            previousValue = screenUiState.previousValue
            unitOfMeasurement = screenUiState.unitOfMeasurement
        }

        is Content -> {
            name = screenUiState.name
            tariff = screenUiState.tariff
            isMeterAvailable = screenUiState.isMeterAvailable
            previousValue = screenUiState.previousValue
            unitOfMeasurement = screenUiState.unitOfMeasurement
        }

        is Error -> {
            isNameEmpty = screenUiState.isNameEmpty
            isTariffEmpty = screenUiState.isTariffEmpty
            isTariffNotDouble = screenUiState.isTariffNotDouble
            isPreviousValueEmpty = screenUiState.isPreviousValueEmpty
            isPreviousValueNotDigit = screenUiState.isPreviousValueNotDigit
        }
    }

    Column(
        modifier = modifier
    ) {
        CustomOutlinedTextField(
            value = name,
            onValueChange = { name = it.trim() },
            isError = isNameEmpty,
            labelText = "Назва послуги:"
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))

        CustomOutlinedTextField(
            value = tariff,
            onValueChange = { tariff = it.trim() },
            isError = (isTariffEmpty || isTariffNotDouble),
            labelText = "Тариф:"
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isMeterAvailable,
                onCheckedChange = { isMeterAvailable = !isMeterAvailable }
            )
            Text(
                text = "Наявність лічильника",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))

        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            CustomOutlinedTextField(
                modifier = Modifier.weight(1f),
                value = previousValue,
                onValueChange = { previousValue = it.trim() },
                isError = (isPreviousValueEmpty || isPreviousValueNotDigit),
                labelText = "Попередні значення:"
            )

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.width_medium)))

            MeasurementExposeDropdownMenuBox(
                modifier = Modifier.width(100.dp),
                onValueChange = { })
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_large)))

        PrimaryButton(
            text = "Зберегти",
            onClick = {
                onSaveButtonClick(
                    name,
                    tariff,
                    isMeterAvailable,
                    previousValue,
                    unitOfMeasurement
                )
            }
        )
    }

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun InsertUtilityServiceScreenPreview() {
    val screenUiState = Initial(
        name = "",
        tariff = "",
        isMeterAvailable = true,
        previousValue = "0",
        unitOfMeasurement = MeasurementUnit.CUBIC_METER
    )
    UtilityBillsTheme {
        InsertUtilityServiceScreen(
            modifier = Modifier.padding(16.dp),
            screenUiState = screenUiState,
            onSaveButtonClick = { _, _, _, _, _ -> }
        )
    }
}

