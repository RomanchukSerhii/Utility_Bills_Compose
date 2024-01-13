package com.serhiiromanchuk.utilitybills.presentation.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.compose.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.mocks.fakeUtilityService
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.presentation.core.components.BodyTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.EditServiceIcon
import com.serhiiromanchuk.utilitybills.presentation.core.components.LabelTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.RoundCheckBox
import com.serhiiromanchuk.utilitybills.presentation.core.components.RoundCheckBoxDefaults
import com.serhiiromanchuk.utilitybills.presentation.core.components.TitleTextOnSurface


@Composable
fun ServiceItem(
    modifier: Modifier = Modifier,
    utilityService: UtilityServiceItem,
    previousValueChange: (Int) -> Unit,
    currentValueChange: (Int) -> Unit,
    onEditServiceClick: () -> Unit,
    isEnabled: (Boolean) -> Unit
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
            Row(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                verticalAlignment =  Alignment.Top
            ) {
                var isChecked by rememberSaveable {
                    mutableStateOf(utilityService.isMeterAvailable)
                }
                RoundCheckBox(
                    isChecked = isChecked,
                    onClick = {
                        isChecked = !isChecked
                        isEnabled(isChecked)
                    },
                    color = RoundCheckBoxDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.tertiary,
                        disabledSelectedColor = MaterialTheme.colorScheme.surface,
                        borderColor = if (isChecked){
                            MaterialTheme.colorScheme.tertiary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.width_small)))
                ServiceDetails(
                    modifier = Modifier.weight(1f),
                    utilityService = utilityService,
                    previousValueChange = previousValueChange,
                    currentValueChange = currentValueChange
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.width_small)))
                EditServiceIcon(onEditServiceClick = onEditServiceClick)
            }

    }
}

@Composable
private fun ServiceDetails(
    modifier: Modifier = Modifier,
    utilityService: UtilityServiceItem,
    previousValueChange: (Int) -> Unit,
    currentValueChange: (Int) -> Unit
) {
    Column(modifier = modifier) {
        TitleTextOnSurface(text = utilityService.name)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_extra_small)))
        BodyTextOnSurface(text = stringResource(R.string.tariff, utilityService.tariff))
        if (utilityService.isMeterAvailable) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
            MeterValue(
                utilityService = utilityService,
                previousValueChange = previousValueChange,
                currentValueChange = currentValueChange
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))
    }
}


@Composable
private fun MeterValue(
    modifier: Modifier = Modifier,
    utilityService: UtilityServiceItem,
    previousValueChange: (Int) -> Unit,
    currentValueChange: (Int) -> Unit
) {
    var previousValue by rememberSaveable {
        mutableStateOf(utilityService.previousValue.toString())
    }
    var currentValue by rememberSaveable {
        mutableStateOf(utilityService.currentValue.toString())
    }

    var previousValueDigit = utilityService.previousValue
    if (previousValue.isDigitsOnly()) {
        previousValueDigit = previousValue.toInt()
    }

    var currentValueDigit = utilityService.currentValue
    if (currentValue.isDigitsOnly()) {
        currentValueDigit = currentValue.toInt()
    }

    Row(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            LabelTextOnSurface(text = "Попередні")
            TextField(
                value = previousValue,
                onValueChange = {
                    previousValue = it.trim()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                isError = previousValue.isDigitsOnly()
            )
        }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            LabelTextOnSurface(text = "Поточні")
            TextField(
                value = currentValue,
                onValueChange = {
                    currentValue = it.trim()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                isError = currentValue.isDigitsOnly() || currentValueDigit < previousValueDigit
            )
        }
    }
}

@Preview
@Composable
fun PreviewDetailsItem() {
    UtilityBillsTheme {
        ServiceDetails(
            utilityService = fakeUtilityService,
            previousValueChange = {},
            currentValueChange = {})
    }
}

@Preview
@Composable
fun PreviewServiceItem() {
    UtilityBillsTheme {
        ServiceItem(
            utilityService = fakeUtilityService,
            previousValueChange = {},
            currentValueChange = {},
            onEditServiceClick = { /*TODO*/ },
            isEnabled = {}
        )
    }
}