package com.serhiiromanchuk.utilitybills.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.presentation.core.components.AddressExposeDropdownMenuBox
import com.serhiiromanchuk.utilitybills.presentation.core.components.LabelTextOnPrimary
import com.serhiiromanchuk.utilitybills.presentation.core.getCurrentMonth
import com.serhiiromanchuk.utilitybills.presentation.core.getSaveCardNumber

@Composable
fun BillLayout(
    modifier: Modifier = Modifier,
    utilityService: UtilityServiceItem,
    onEditClick: () -> Unit,
    onCheckIconClick: (Boolean) -> Unit,
    previousValueChange: (Int) -> Unit,
    currentValueChange: (Int) -> Unit
) {
    Column {
        val utilityServices: List<UtilityServiceItem> = emptyList()
        val addressListTest = listOf("Грушевського 23, кв.235", "Грушевського 23, кв.171")
        BillHeader(
            billItem = BillItem(
                address = addressListTest[0],
                month = "Листопад",
                year = 2023,
                cardNumber = "4444 2514 2684 1354",
                utilityServices = utilityServices
            ),
            addressList = addressListTest,
            onCardNumberEditClick = { /*TODO*/ },
            changeBillAddress = {}
        ) {

        }
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            Row {
                var isChecked by rememberSaveable { mutableStateOf(utilityService.isMeterAvailable) }
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked = !isChecked
                        onCheckIconClick(isChecked)
                    }
                )
                UtilityServiceDetails(
                    modifier = Modifier
                        .padding(vertical = dimensionResource(id = R.dimen.padding_medium))
                        .weight(1f),
                    utilityService = utilityService,
                    previousValueChange = previousValueChange,
                    currentValueChange = currentValueChange
                )
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
    }

}

@Composable
private fun BillHeader(
    modifier: Modifier = Modifier,
    billItem: BillItem,
    addressList: List<String>,
    onCardNumberEditClick: () -> Unit,
    changeBillAddress: (String) -> Unit,
    onAddNewAddressClick: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BillCardNumber(
                onCardNumberEditClick = onCardNumberEditClick,
                cardNumber = billItem.cardNumber,
                changeBillAddress = {}
            )
            BillMonth()
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_medium)))
        BillAddress(
            currentAddress = billItem.address,
            addressList = addressList,
            onValueChange = changeBillAddress,
            onAddNewAddressClick = onAddNewAddressClick
        )
    }
}

@Composable
private fun BillCardNumber(
    modifier: Modifier = Modifier,
    onCardNumberEditClick: () -> Unit,
    changeBillAddress: (String) -> Unit,
    cardNumber: String
) {
    var isEditable by rememberSaveable { mutableStateOf(false) }
    var currentCardNumber by rememberSaveable { mutableStateOf(getSaveCardNumber(cardNumber)) }

    Column {
        LabelTextOnPrimary(text = "Картка")

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_extra_small)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = currentCardNumber,
                onValueChange = {
                    currentCardNumber = it
                },
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        isEditable = false
                    }
                ),
                readOnly = !isEditable
            )

            IconButton(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                    .size(20.dp),
                onClick = {
                    isEditable = !isEditable
                }
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = if (isEditable) Icons.Rounded.Check else Icons.Rounded.Edit,
                    contentDescription = stringResource(R.string.edit_card_number),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
private fun BillMonth(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {

        LabelTextOnPrimary(text = "Місяць")
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_extra_small)))
        Text(
            text = getCurrentMonth(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
private fun BillAddress(
    modifier: Modifier = Modifier,
    currentAddress: String,
    addressList: List<String>,
    onValueChange: (String) -> Unit,
    onAddNewAddressClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        AddressExposeDropdownMenuBox(
            currentAddress = currentAddress,
            addressList = addressList,
            onValueChange = onValueChange
        )
        IconButton(
            onClick = { onAddNewAddressClick() }
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Додати нову адресу рахунку",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
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
        Text(
            text = utilityService.name,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_extra_small)))
        Text(
            text = stringResource(R.string.tariff, utilityService.tariff),
            style = MaterialTheme.typography.bodyMedium
        )
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
            Text(
                text = "Попередні",
                style = MaterialTheme.typography.labelMedium
            )
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
            Text(
                text = "Поточні",
                style = MaterialTheme.typography.labelMedium
            )
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


val utilityServiceTest = UtilityServiceItem(
    name = "Газ",
    address = "Грушевского 23, 235",
    tariff = 7.98,
    isMeterAvailable = true,
    previousValue = 9624,
)

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun UtilityServiceLayoutPreview() {
    UtilityBillsTheme(darkTheme = false) {
        Column {
            BillLayout(
                utilityService = utilityServiceTest,
                onEditClick = { /*TODO*/ },
                onCheckIconClick = { /*TODO*/ },
                previousValueChange = {},
                currentValueChange = {}
            )
        }

    }
}