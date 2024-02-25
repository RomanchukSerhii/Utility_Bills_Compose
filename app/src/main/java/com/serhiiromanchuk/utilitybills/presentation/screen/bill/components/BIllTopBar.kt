package com.serhiiromanchuk.utilitybills.presentation.screen.bill.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.mocks.fakeBillItem
import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.presentation.core.components.BodyTextOnPrimary
import com.serhiiromanchuk.utilitybills.presentation.core.components.HeadlineTextOnPrimary
import com.serhiiromanchuk.utilitybills.presentation.core.components.LabelTextOnPrimary
import com.serhiiromanchuk.utilitybills.presentation.core.components.TopBarApp
import com.serhiiromanchuk.utilitybills.presentation.screen.bill.BillUiEvent
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme

@Composable
fun BillTopBar(
    modifier: Modifier = Modifier,
    billItem: BillItem,
    onEvent: (BillUiEvent) -> Unit
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
    ) {
        TopBarApp(
            onBackPressed = { onEvent(BillUiEvent.OnBackClicked) },
            actions = {
                IconButton(onClick = {  }) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            gradientColors = listOf(Color.Transparent, Color.Transparent)
        )

        Column(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.padding_large),
                bottom = dimensionResource(id = R.dimen.padding_large),
            )
        ) {
            AddressText(address = billItem.address)
            Row(
                modifier = Modifier.padding(end = dimensionResource(id = R.dimen.height_small)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BillInfoText(label = "Платник", infoText = billItem.payerName)
                Spacer(modifier = Modifier.weight(1f))
                EditIcon(onEvent = onEvent)
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))
            BillInfoText(label = "Місяць", infoText = "${billItem.month} ${billItem.year}")
        }
    }
}

@Composable
private fun AddressText(
    address: String
) {
    Column(
        modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_large)),
    ) {
        HeadlineTextOnPrimary(text = address)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_medium)))
        Row {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.width(40.dp))
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_medium)))
    }
}

@Composable
fun BillInfoText(
    label: String,
    infoText: String
) {
    Column {
        LabelTextOnPrimary(text = label)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_extra_small)))
        BodyTextOnPrimary(text = infoText)
    }
}

@Composable
fun EditIcon(
    onEvent: (BillUiEvent) -> Unit
) {
    IconButton(onClick = { onEvent(BillUiEvent.EditBillInfo) }) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = R.drawable.ic_edit),
            contentDescription = stringResource(R.string.edit_bill_info),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
private fun PreviewHomeScreenHeader() {
    UtilityBillsTheme(darkTheme = false) {
        BillTopBar(
            billItem = fakeBillItem,
            onEvent = {}
        )
    }
}
