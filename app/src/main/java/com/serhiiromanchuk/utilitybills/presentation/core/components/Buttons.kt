package com.serhiiromanchuk.utilitybills.presentation.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.ui.theme.disabledButtonContainer
import com.serhiiromanchuk.utilitybills.ui.theme.disabledButtonContent

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Box(
        modifier = modifier
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            shape = MaterialTheme.shapes.medium,
            enabled = enabled,
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                disabledContainerColor = disabledButtonContainer,
                disabledContentColor = disabledButtonContent
            )
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Box(
        modifier = modifier
    ) {
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = MaterialTheme.shapes.small,
            enabled = enabled,
            onClick = onClick,
            border = BorderStroke(
                width = 1.dp,
                color = if (enabled) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                }
            )
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonsPreview() {
    UtilityBillsTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryButton(
                text = "Сформувати рахунок",
                onClick = {}
            )
            PrimaryButton(
                text = "Сформувати рахунок",
                onClick = {},
                enabled = false
            )
            SecondaryButton(text = "Деталі рахунку", onClick = { /*TODO*/ })
            SecondaryButton(text = "Деталі рахунку", onClick = { /*TODO*/ }, enabled = false)
        }
    }
}