package com.serhiiromanchuk.utilitybills.presentation.screen.choose_bill.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.BillCardIcon
import com.serhiiromanchuk.utilitybills.presentation.core.components.CardOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.TextOnBillCard
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BillAddressCard(
    modifier: Modifier = Modifier,
    billAddress: String,
    onLongClick: () -> Unit,
    onClick: () -> Unit
) {
    CardOnSurface(
        modifier = modifier.combinedClickable(
            onLongClick = onLongClick,
            onClick = onClick
        )
    ) {
        BillCardIcon(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            iconId = R.drawable.ic_home,
            contentDescriptionId = null
        )
        TextOnBillCard(
            modifier = Modifier.weight(1f),
            text = billAddress
        )
    }
}

@DarkLightPreviews
@Composable
private fun AddNewBillPreview() {
    UtilityBillsTheme {
        BillAddressCard(
            billAddress = "вул. Пилипа Орлика 14, кв.3",
            onLongClick = { },
            onClick = { }
        )
    }
}