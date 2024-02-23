package com.serhiiromanchuk.utilitybills.presentation.screen.bill.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.presentation.core.components.AddressExposeDropdownMenuBox
import com.serhiiromanchuk.utilitybills.presentation.core.components.BodyTextOnPrimary
import com.serhiiromanchuk.utilitybills.presentation.core.components.LabelTextOnPrimary
import com.serhiiromanchuk.utilitybills.presentation.screen.bill.BillUiEvent
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme

@Composable
fun BillScreenTopBar(
    modifier: Modifier = Modifier,
    billItem: BillItem,
    onEvent: (BillUiEvent) -> Unit,
    onBackPressed: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primaryContainer
                    )
                )
            )
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {

        Row {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.back_to_previous_screen)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        
        Text(text = billItem.address)


//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            BillCardNumber(
//                saveCardNumber = { /*TODO*/ },
//                billItem = billItem
//            )
//            BillMonth(billItem = billItem)
//        }
//        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_medium)))
//        BillAddress(
//            currentAddress = billItem.address,
//            addressList = addressList,
//            onValueChange = changeBillAddress
//        )
    }
}

//@Composable
//private fun BillCardNumber(
//    modifier: Modifier = Modifier,
//    saveCardNumber: (String) -> Unit,
//    billItem: BillItem
//) {
//    var isEditable by rememberSaveable { mutableStateOf(false) }
//    var maskingCardNumber by rememberSaveable { mutableStateOf(getMaskingCardNumber(billItem.cardNumber)) }
//    var unmaskingCardNumber by rememberSaveable { mutableStateOf(formatToCardNumberType(billItem.cardNumber)) }
//    val focusRequester by remember { mutableStateOf(FocusRequester()) }
//
//    Column(
//        modifier = modifier
//    ) {
//        LabelTextOnPrimary(text = "Картка")
//
//        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_extra_small)))
//
//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            BasicTextField(
//                modifier = Modifier.focusRequester(focusRequester),
//                value = if (!isEditable) {
//                    TextFieldValue(
//                        maskingCardNumber,
//                        selection = TextRange(maskingCardNumber.length)
//                    )
//                } else {
//                    TextFieldValue(
//                        unmaskingCardNumber,
//                        selection = TextRange(unmaskingCardNumber.length)
//                    )
//                },
//                onValueChange = {
//                    unmaskingCardNumber = formatToCardNumberType(it.text)
//                },
//                textStyle = MaterialTheme.typography.bodyMedium.copy(
//                    color = MaterialTheme.colorScheme.onPrimary
//                ),
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Number,
//                    imeAction = ImeAction.Done
//                ),
//                keyboardActions = KeyboardActions(
//                    onDone = {
//                        isEditable = false
//                    }
//                ),
//                readOnly = !isEditable,
//                cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimary)
//            )
//
//            IconButton(
//                modifier = Modifier
//                    .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
//                    .size(20.dp),
//                onClick = {
//                    if (isEditable) {
//                        // If the number of credit card characters is full - save a new card, otherwise save the previous card number
//                        if (unmaskingCardNumber.length == 19) {
//                            saveCardNumber(unmaskingCardNumber)
//                            maskingCardNumber = getMaskingCardNumber(unmaskingCardNumber)
//                        } else {
//                            unmaskingCardNumber = billItem.cardNumber
//                            maskingCardNumber = getMaskingCardNumber(billItem.cardNumber)
//                        }
//                    } else {
//                        focusRequester.requestFocus()
//                    }
//                    isEditable = !isEditable
//                }
//            ) {
//                Icon(
//                    modifier = Modifier.size(20.dp),
//                    imageVector = if (isEditable) Icons.Rounded.Check else Icons.Rounded.Edit,
//                    contentDescription = stringResource(R.string.edit_card_number),
//                    tint = MaterialTheme.colorScheme.onPrimary
//                )
//            }
//        }
//    }
//}

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
) {
    AddressExposeDropdownMenuBox(
        modifier = modifier,
        currentAddress = currentAddress,
        addressList = addressList,
        onValueChange = onValueChange
    )
}

@Preview
@Composable
private fun PreviewHomeScreenHeader() {
    UtilityBillsTheme(darkTheme = false) {
//        BillScreenTopBar(
//            billItem = BillItem(
//                address = "Грушевского 23, кв. 235",
//                month = "Січень",
//                year = "2024",
//                indexPosition = 0,
//                cardNumber = "4152 1897 4931 2468"
//            ),
//            addressList = listOf("Грушевского 23, кв. 235", "Грушевского 23, кв. 171"),
//            onCardNumberEditClick = { /*TODO*/ },
//            changeBillAddress = {}
//        ) {
//
//        }
    }
}
