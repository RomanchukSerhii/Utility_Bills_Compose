package com.serhiiromanchuk.utilitybills.presentation.screen.bill_details.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.serhiiromanchuk.utilitybills.R

@Composable
fun BillTotalText(
    value: String
) {
    Text(
        text = stringResource(R.string.total_bill_value, value),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
}