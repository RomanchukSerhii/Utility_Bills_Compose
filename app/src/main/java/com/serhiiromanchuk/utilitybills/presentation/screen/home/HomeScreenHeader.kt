package com.serhiiromanchuk.utilitybills.presentation.screen.home

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
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.presentation.core.components.AddressExposeDropdownMenuBox
import com.serhiiromanchuk.utilitybills.presentation.core.components.BodyTextOnPrimary
import com.serhiiromanchuk.utilitybills.presentation.core.components.LabelTextOnPrimary
import com.serhiiromanchuk.utilitybills.utils.formatToCardNumberType
import com.serhiiromanchuk.utilitybills.utils.getMaskingCardNumber

@Composable
fun HomeScreenHeader(
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
                saveCardNumber = { /*TODO*/ },
                billItem = billItem
            )
            BillMonth(billItem = billItem)
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
    saveCardNumber: (String) -> Unit,
    billItem: BillItem
) {
    var isEditable by rememberSaveable { mutableStateOf(false) }
    var maskingCardNumber by rememberSaveable { mutableStateOf(getMaskingCardNumber(billItem.cardNumber)) }
    var unmaskingCardNumber by rememberSaveable { mutableStateOf(formatToCardNumberType(billItem.cardNumber)) }
    val focusRequester by remember { mutableStateOf(FocusRequester()) }

    Column(
        modifier = modifier
    ) {
        LabelTextOnPrimary(text = "Картка")

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_extra_small)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                modifier = Modifier.focusRequester(focusRequester),
                value = if (!isEditable) {
                    TextFieldValue(maskingCardNumber, selection = TextRange(maskingCardNumber.length))
                } else {
                    TextFieldValue(unmaskingCardNumber, selection = TextRange(unmaskingCardNumber.length))
                },
                onValueChange = {
                    unmaskingCardNumber = formatToCardNumberType(it.text)
                },
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        isEditable = false
                    }
                ),
                readOnly = !isEditable,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimary)
            )

            IconButton(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                    .size(20.dp),
                onClick = {
                    if (isEditable) {
                        // If the number of credit card characters is full - save a new card, otherwise save the previous card number
                        if (unmaskingCardNumber.length == 19) {
                            saveCardNumber(unmaskingCardNumber)
                            maskingCardNumber = getMaskingCardNumber(unmaskingCardNumber)
                        } else {
                            unmaskingCardNumber = billItem.cardNumber
                            maskingCardNumber = getMaskingCardNumber(billItem.cardNumber)
                        }
                    } else {
                        focusRequester.requestFocus()
                    }
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
    modifier: Modifier = Modifier,
    billItem: BillItem
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        LabelTextOnPrimary(text = stringResource(R.string.month))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_extra_small)))
        BodyTextOnPrimary(text = billItem.month)
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
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        AddressExposeDropdownMenuBox(
            currentAddress = currentAddress,
            addressList = addressList,
            onValueChange = onValueChange
        )
    }
}

@Preview
@Composable
private fun PreviewHomeScreenHeader() {
    UtilityBillsTheme(darkTheme = false) {
        HomeScreenHeader(
            billItem = BillItem(
                address = "Грушевского 23, кв. 235",
                month = "Січень",
                year = "2024",
                cardNumber = "4152 1897 4931 2468"
            ),
            addressList = listOf("Грушевского 23, кв. 235", "Грушевского 23, кв. 171"),
            onCardNumberEditClick = { /*TODO*/ },
            changeBillAddress = {}
        ) {
            
        }
    }
}