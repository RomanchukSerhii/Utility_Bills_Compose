package com.serhiiromanchuk.utilitybills.presentation.core.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.serhiiromanchuk.utilitybills.R

@Composable
fun LabelTextOnPrimary(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun LabelTextOnSurface(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun TitleTextOnSurface(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun BodyTextOnSurface(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        lineHeight = 14.sp
    )
}

@Composable
fun BodyTextOnPrimary(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onPrimary,
    )
}

@Composable
fun ErrorSupportingText(
    modifier: Modifier = Modifier,
    @StringRes supportingText: Int,
    isEnabled: Boolean
) {
    if (isEnabled) {
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.weight(3f)
            ) {
                ErrorPointer(color = MaterialTheme.colorScheme.error)
                Text(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onSurface)
                        .padding(
                            start = dimensionResource(id = R.dimen.height_small),
                            end = dimensionResource(id = R.dimen.height_small),
                            bottom = dimensionResource(id = R.dimen.height_small),
                            top = dimensionResource(id = R.dimen.height_extra_small),
                        ),
                    text = stringResource(id = supportingText),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    lineHeight = 14.sp
                )
            }
        }
    }
}