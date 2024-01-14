package com.serhiiromanchuk.utilitybills.presentation.core.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme

@Composable
fun EditServiceIcon(
    modifier: Modifier = Modifier,
    onEditServiceClick: () -> Unit
) {
    IconButton(
        modifier = modifier.size(36.dp),
        onClick = onEditServiceClick
    ) {
        Icon(
            modifier = Modifier.size(36.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
            contentDescription = stringResource(R.string.edit_meter_value),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview
@Composable
fun PreviewEditServiceIcon() {
    UtilityBillsTheme {
        EditServiceIcon(onEditServiceClick = {})
    }
}