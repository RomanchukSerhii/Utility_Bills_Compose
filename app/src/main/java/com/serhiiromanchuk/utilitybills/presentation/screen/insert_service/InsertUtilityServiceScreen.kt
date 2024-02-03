package com.serhiiromanchuk.utilitybills.presentation.screen.insert_service

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.ErrorTextMessage
import com.serhiiromanchuk.utilitybills.presentation.core.components.MeasurementExposeDropdownMenuBox
import com.serhiiromanchuk.utilitybills.presentation.core.components.OutlinedTextFieldOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.PrimaryButton
import com.serhiiromanchuk.utilitybills.presentation.core.components.RoundCheckBox
import com.serhiiromanchuk.utilitybills.presentation.core.components.RoundCheckBoxDefaults
import com.serhiiromanchuk.utilitybills.presentation.core.components.TopBarApp
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
    utilityServiceId: Long,
    billCreatorId: Long,
    onBackPressed: () -> Unit
) {
    val component = getApplicationComponent()
        .getInsertServiceScreenComponentFactory()
        .create(utilityServiceId, billCreatorId)
    val viewModel: InsertUtilityServiceViewModel =
        viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                InsertUtilityServiceViewModel.ValidationEvent.Success -> onBackPressed()
            }
        }
    }

    Scaffold(
        topBar = {
            TopBarApp(
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
                modifier = modifier.fillMaxWidth(),
                text = "Зберегти",
                onClick = {
                    viewModel.onEvent(InsertServiceFormEvent.Submit)
                }
            )
        }
    ) {
        InsertUtilityServiceContent(
            modifier = modifier.padding(it),
            screenState = screenState.value,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun InsertUtilityServiceContent(
    modifier: Modifier = Modifier,
    screenState: InsertServiceFormState,
    onEvent: (InsertServiceFormEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        val focusManager = LocalFocusManager.current
        val context = LocalContext.current
        val interactionSource = remember { MutableInteractionSource() }
        val isFocused by interactionSource.collectIsFocusedAsState()

        OutlinedTextFieldOnSurface(
            value = screenState.name,
            onValueChange = { onEvent(InsertServiceFormEvent.NameChanged(it)) },
            isError = screenState.nameError != null,
            labelText = "Назва послуги:",
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )

        if (screenState.nameError != null) {
            ErrorTextMessage(text = screenState.nameError.asString())
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))

        OutlinedTextFieldOnSurface(
            value = getTariffValue(isFocused, screenState.tariff),
            onValueChange = {
                if (it.isPriceFormat()) {
                    onEvent(InsertServiceFormEvent.TariffChanged(it.replaceComaToDot()))
                }
            },
            isError = screenState.tariffError != null,
            labelText = "Тариф:",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            visualTransformation = { input -> getPriceTransformedText(input, context) },
            interactionSource = interactionSource
        )

        if (screenState.tariffError != null) {
            ErrorTextMessage(text = screenState.tariffError.asString())
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundCheckBox(
                isChecked = screenState.isMeterAvailable,
                onClick = { onEvent(InsertServiceFormEvent.MeterAvailableChanged(it)) },
                color = RoundCheckBoxDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.tertiary,
                    disabledSelectedColor = MaterialTheme.colorScheme.surface,
                    borderColor = if (screenState.isMeterAvailable) {
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

        if (screenState.isMeterAvailable) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                OutlinedTextFieldOnSurface(
                    modifier = Modifier.weight(1f),
                    value = screenState.previousValue,
                    onValueChange = {
                        val formattedValue = it.getFormattedDigitsOnly(8)
                        onEvent(InsertServiceFormEvent.PreviousValueChanged(formattedValue))
                    },
                    isError = screenState.previousValueError != null,
                    labelText = "Попередні значення:",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    visualTransformation = { input -> getUtilityMeterTransformedText(input) },
                )

                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.width_medium)))

                MeasurementExposeDropdownMenuBox(
                    modifier = Modifier.width(110.dp),
                    onValueChange = { onEvent(InsertServiceFormEvent.UnitOfMeasurementChanged(it)) }
                )
            }

            if (screenState.previousValueError != null) {
                ErrorTextMessage(text = screenState.previousValueError.asString())
            }
        }
    }
}

private fun getTariffValue(isFocused: Boolean, value: String): String {
    return if (isFocused && value == "0") ""
    else if (!isFocused && value == "") "0"
    else value
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
                screenState = InsertServiceFormState(),
                onEvent = {}
            )
        }
    }
}

