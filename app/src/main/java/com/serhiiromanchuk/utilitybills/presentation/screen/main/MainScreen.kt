package com.serhiiromanchuk.utilitybills.presentation.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.compose.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.presentation.core.components.BodyTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.LabelTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.PrimaryButton
import com.serhiiromanchuk.utilitybills.presentation.core.components.TitleTextOnSurface

@Composable
fun MainScreenLayout(
    modifier: Modifier = Modifier,
    mainScreenUiState: MainScreenUiState,
    onDeleteButtonClick: () -> Unit,
    onEditClick: () -> Unit,
    onCheckIconClick: (Boolean) -> Unit,
    previousValueChange: (Int) -> Unit,
    currentValueChange: (Int) -> Unit
) {
    Column {
        val addressListTest = listOf("Грушевського 23, кв.235", "Грушевського 23, кв.171")

//        HomeScreenHeader(
//            billItem = BillItem(
//                address = addressListTest[0],
//                month = month,
//                year = 2023,
//                cardNumber = "4444 2514 2684 1354"
//            ),
//            addressList = addressListTest,
//            onCardNumberEditClick = { /*TODO*/ },
//            changeBillAddress = {}
//        ) {
//
//        }
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            Row {
                var isChecked by rememberSaveable { mutableStateOf(true) }
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked = !isChecked
                        onCheckIconClick(isChecked)
                    }
                )
//                UtilityServiceDetails(
//                    modifier = Modifier
//                        .padding(vertical = dimensionResource(id = R.dimen.padding_medium))
//                        .weight(1f),
//                    utilityService = utilityServiceTest,
//                    previousValueChange = previousValueChange,
//                    currentValueChange = currentValueChange
//                )
                IconButton(onClick = onEditClick) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
                        contentDescription = stringResource(R.string.edit_meter_value),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        PrimaryButton(text = "Видалити", onClick = onDeleteButtonClick )
    }

}

@Composable
private fun UtilityServiceDetails(
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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


@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun UtilityServiceLayoutPreview() {
    UtilityBillsTheme(darkTheme = false) {
        Column {
            MainScreenLayout(
                mainScreenUiState = MainScreenUiState.Initial,
                onDeleteButtonClick = {},
                onEditClick = { /*TODO*/ },
                onCheckIconClick = { /*TODO*/ },
                previousValueChange = {},
                currentValueChange = {}
            )
        }

    }
}