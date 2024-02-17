package com.serhiiromanchuk.utilitybills.presentation.core.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
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
fun HeadlineTextOnPrimary(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onPrimary,
        maxLines = maxLines,
        overflow = overflow
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
    style: TextStyle = MaterialTheme.typography.titleMedium,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun BodyTextOnSurface(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    lineHeight: TextUnit = 14.sp,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = color,
        lineHeight = lineHeight,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
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
fun TextOnBillCard(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.padding_small),
                    end = dimensionResource(id = R.dimen.padding_small),
                    bottom = dimensionResource(id = R.dimen.padding_medium),
                    top = dimensionResource(id = R.dimen.padding_small),
                ),
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
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

@Composable
fun ErrorTextMessage(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.error,
    lineHeight: TextUnit = 14.sp
) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = color,
        lineHeight = lineHeight
    )
}