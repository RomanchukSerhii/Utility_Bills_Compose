package com.serhiiromanchuk.utilitybills.presentation.screen.choose_bill.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.BillCardIcon
import com.serhiiromanchuk.utilitybills.presentation.core.components.CardOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.TextOnBillCard
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme

@Composable
fun AddNewBill(
    modifier: Modifier = Modifier,
    onAddBillClick: () -> Unit
) {
    CardOnSurface(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_extra_small))
    ) {
        Column(
            modifier = Modifier
                .clickable { onAddBillClick() }
        ) {
            BillCardIcon(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                iconId = R.drawable.ic_add_folder,
                contentDescriptionId = R.string.add_bill_address,
                isAddIcon = true
            )
            TextOnBillCard(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.add_bill_address)
            )
        }
    }
}

@DarkLightPreviews
@Composable
private fun AddNewBillPreview() {
    UtilityBillsTheme() {
        AddNewBill(
            onAddBillClick = {}
        )
    }
}