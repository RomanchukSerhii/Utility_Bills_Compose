package com.serhiiromanchuk.utilitybills.presentation.screen.insertservice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.model.MeasurementUnit
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.HeadlineTextOnPrimary
import com.serhiiromanchuk.utilitybills.presentation.core.components.MeasurementExposeDropdownMenuBox
import com.serhiiromanchuk.utilitybills.presentation.core.components.OutlinedTextFieldOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.PrimaryButton
import com.serhiiromanchuk.utilitybills.presentation.core.components.RoundCheckBox
import com.serhiiromanchuk.utilitybills.presentation.core.components.RoundCheckBoxDefaults
import com.serhiiromanchuk.utilitybills.presentation.core.components.SecondaryHeader
import com.serhiiromanchuk.utilitybills.presentation.getApplicationComponent
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.utils.getFormattedDigitsOnly
import com.serhiiromanchuk.utilitybills.utils.getPriceTransformedText
import com.serhiiromanchuk.utilitybills.utils.getUtilityMeterTransformedText
import com.serhiiromanchuk.utilitybills.utils.isPriceFormat
import com.serhiiromanchuk.utilitybills.utils.replaceComaToDot


@Composable
fun InsertUtilityServiceScreen(
    modifier: Modifier = Modifier,
    utilityServiceId: Int,
    onBackPressed: () -> Unit
) {
    val component = getApplicationComponent()
        .getInsertServiceScreenComponentFactory()
        .create(utilityServiceId)
    val viewModel: InsertUtilityServiceViewModel =
        viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsState()

    Scaffold(
        topBar = {
            SecondaryHeader(
                titleId = if (utilityServiceId > 0) {
                    R.string.insert_service_title
                } else {
                    R.string.add_service_title
                },
                onBackPressed = onBackPressed
            )
        },
        bottomBar = {
            PrimaryButton(
                modifier = modifier
                    .fillMaxWidth(),
                text = "Зберегти",
                onClick = { }
            )
        }
    ) {
        InsertUtilityServiceContent(
            modifier = modifier.padding(it),
            screenState = screenState,
            onNameChanged = { name ->
                viewModel.onEvent(InsertServiceFormEvent.NameChanged(name))
            },
            onTariffChanged = { tariff ->
                viewModel.onEvent(InsertServiceFormEvent.TariffChanged(tariff))
            },
            onMeterAvailableChanged = { isAvailable ->
                viewModel.onEvent(InsertServiceFormEvent.MeterAvailableChanged(isAvailable))
            },
            onPreviousValueChanged = { previousValue ->
                viewModel.onEvent(InsertServiceFormEvent.PreviousValueChanged(previousValue))
            },
            onMeasurementUnitChanged = { measurementUnit ->
                viewModel.onEvent(InsertServiceFormEvent.UnitOfMeasurementChanged(measurementUnit))
            }
        )
    }
}

@Composable
private fun InsertUtilityServiceContent(
    modifier: Modifier = Modifier,
    screenState: State<InsertServiceFormState>,
    onNameChanged: (String) -> Unit,
    onTariffChanged: (String) -> Unit,
    onMeterAvailableChanged: (Boolean) -> Unit,
    onPreviousValueChanged: (String) -> Unit,
    onMeasurementUnitChanged: (MeasurementUnit) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        val context = LocalContext.current
        OutlinedTextFieldOnSurface(
            value = screenState.value.name,
            onValueChange = onNameChanged,
            isError = screenState.value.nameError != null,
            labelText = "Назва послуги:",
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))

        OutlinedTextFieldOnSurface(
            value = screenState.value.tariff,
            onValueChange = {
                if (it.isPriceFormat()) {
                    onTariffChanged(it.replaceComaToDot())
                }
            },
            isError = screenState.value.tariffError != null,
            labelText = "Тариф:",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            visualTransformation = { input -> getPriceTransformedText(input, context) }
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundCheckBox(
                isChecked = screenState.value.isMeterAvailable,
                onClick = onMeterAvailableChanged,
                color = RoundCheckBoxDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.tertiary,
                    disabledSelectedColor = MaterialTheme.colorScheme.surface,
                    borderColor = if (screenState.value.isMeterAvailable) {
                        MaterialTheme.colorScheme.tertiary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            )
            Text(
                text = "Наявність лічильника",
                style = MaterialTheme.typography.titleMedium
            )
        }

        if (screenState.value.isMeterAvailable) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                OutlinedTextFieldOnSurface(
                    modifier = Modifier.weight(1f),
                    value = screenState.value.previousValue,
                    onValueChange = {
                        val formattedValue = it.getFormattedDigitsOnly(8)
                        onPreviousValueChanged(formattedValue)
                    },
                    isError = screenState.value.previousValueError != null,
                    labelText = "Попередні значення:",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = { input -> getUtilityMeterTransformedText(input) },
                )

                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.width_medium)))

                MeasurementExposeDropdownMenuBox(
                    modifier = Modifier.width(100.dp),
                    onValueChange = onMeasurementUnitChanged
                )
            }
        }
    }
}

@DarkLightPreviews
@Composable
private fun InsertUtilityServiceContentPreview() {

    UtilityBillsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            InsertUtilityServiceContent(
                modifier = Modifier.padding(16.dp),
                screenState = mutableStateOf(InsertServiceFormState()),
                onNameChanged = {},
                onTariffChanged = {},
                onMeterAvailableChanged = {},
                onPreviousValueChanged = {},
                onMeasurementUnitChanged = {}
            )
        }
    }
}

