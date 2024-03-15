package com.serhiiromanchuk.utilitybills.presentation.screen.bill_details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.BodyTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.CardOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.SecondaryButton
import com.serhiiromanchuk.utilitybills.presentation.core.components.TitleTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_details.BillUiEvent
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme

@Composable
fun BillCard(
    billDescription: String,
    onEvent: (BillUiEvent) -> Unit
) {
    CardOnSurface(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                TitleTextOnSurface(text = stringResource(id = R.string.bill))
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))
                BodyTextOnSurface(text = billDescription)
                SecondaryButton(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
                    text = stringResource(R.string.bill_details),
                    onClick = { onEvent(BillUiEvent.OnDetailsClicked) }
                )
            }
            IconButton(onClick = { onEvent(BillUiEvent.OnCopyClicked) }) {
                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.TopEnd),
                    painter = painterResource(id = R.drawable.ic_copy),
                    contentDescription = stringResource(R.string.copy_bill_in_buffer),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@DarkLightPreviews
@Composable
private fun DeleteServiceItemDialogPreview() {
    UtilityBillsTheme {
        BillCard(billDescription = "Комуналка:\nГаз: 82 куб. * 8 грн = 656 грн\nОСББ = 286 грн\nРазом: 942 грн", onEvent = {})
    }
}