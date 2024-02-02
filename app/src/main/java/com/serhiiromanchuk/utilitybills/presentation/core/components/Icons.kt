package com.serhiiromanchuk.utilitybills.presentation.core.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
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

@Composable
fun BillCardIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    @StringRes contentDescriptionId: Int? = null,
    isAddIcon: Boolean = false
) {
    val background = if (isAddIcon) {
        SolidColor(MaterialTheme.colorScheme.secondary)
    } else {
        Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.primaryContainer
            )
        )
    }
    Box(
        modifier = modifier
            .padding(top = 24.dp)
            .clip(MaterialTheme.shapes.large)
            .background(background)
            .padding(dimensionResource(id = R.dimen.padding_large)),
        contentAlignment = Alignment.BottomCenter

    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            painter = painterResource(id = iconId),
            contentDescription = contentDescriptionId?.let { stringResource(it) },
            tint = MaterialTheme.colorScheme.onSecondary
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