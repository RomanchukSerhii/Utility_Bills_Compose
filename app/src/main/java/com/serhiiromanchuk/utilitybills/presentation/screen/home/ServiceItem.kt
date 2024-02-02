package com.serhiiromanchuk.utilitybills.presentation.screen.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.mocks.fakeUtilityService
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.BodyTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.CardOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.EditServiceIcon
import com.serhiiromanchuk.utilitybills.presentation.core.components.ErrorSupportingText
import com.serhiiromanchuk.utilitybills.presentation.core.components.RoundCheckBox
import com.serhiiromanchuk.utilitybills.presentation.core.components.RoundCheckBoxDefaults
import com.serhiiromanchuk.utilitybills.presentation.core.components.TitleTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.UtilityMeterTextField
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.utils.trimSpaces


@Composable
fun ServiceItem(
    modifier: Modifier = Modifier,
    utilityService: UtilityServiceItem,
    checked: Boolean = true,
    onPreviousValueChange: (id: Long, value: String) -> Unit,
    onCurrentValueChange: (id: Long, value: String) -> Unit,
    onEditServiceClick: (id: Long) -> Unit,
    isEnabled: (Boolean) -> Unit
) {
    var isChecked by remember { mutableStateOf(checked) }
    CardOnSurface(
        modifier = modifier
            .animateContentSize(
                animationSpec = tween(durationMillis = 800)
            )
    ) {
        Box(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            CheckedIndicator(checked = isChecked)
            ServiceItemContent(
                utilityService = utilityService,
                checked = isChecked,
                onPreviousValueChange = { onPreviousValueChange(utilityService.id, it) },
                onCurrentValueChange = { onCurrentValueChange(utilityService.id, it) },
                onEditServiceClick = onEditServiceClick,
                isEnabled = {
                    isChecked = it
                    isEnabled(it)
                }
            )
        }
    }
}

@Composable
private fun CheckedIndicator(
    modifier: Modifier = Modifier,
    checked: Boolean
) {
    val backgroundColor = if (checked) {
        MaterialTheme.colorScheme.tertiary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val width by animateDpAsState(
        animationSpec = tween(800),
        targetValue = if (checked) 4.dp else 0.dp, label = ""
    )

    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(width)
            .background(backgroundColor)
    )
}

@Composable
private fun ServiceItemContent(
    modifier: Modifier = Modifier,
    utilityService: UtilityServiceItem,
    checked: Boolean = true,
    onPreviousValueChange: (String) -> Unit,
    onCurrentValueChange: (String) -> Unit,
    onEditServiceClick: (id: Long) -> Unit,
    isEnabled: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalAlignment = Alignment.Top
    ) {
        RoundCheckBox(
            isChecked = checked,
            onClick = { isEnabled(!checked) },
            color = RoundCheckBoxDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.tertiary,
                disabledSelectedColor = MaterialTheme.colorScheme.surface,
                borderColor = if (checked) {
                    MaterialTheme.colorScheme.tertiary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.width_small)))
        ServiceDetails(
            modifier = Modifier.weight(1f),
            utilityService = utilityService,
            isChecked = checked,
            onPreviousValueChange = onPreviousValueChange,
            onCurrentValueChange = onCurrentValueChange
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.width_small)))
        EditServiceIcon(onEditServiceClick = { onEditServiceClick(utilityService.id) })
    }
}

@Composable
private fun ServiceDetails(
    modifier: Modifier = Modifier,
    utilityService: UtilityServiceItem,
    isChecked: Boolean,
    onPreviousValueChange: (String) -> Unit,
    onCurrentValueChange: (String) -> Unit
) {
    Column(modifier = modifier) {
        TitleTextOnSurface(text = utilityService.name)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_extra_small)))
        BodyTextOnSurface(text = stringResource(R.string.tariff, utilityService.tariff))
        if (utilityService.isMeterAvailable && isChecked) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
            MeterValue(
                previousValue = utilityService.previousValue,
                currentValue = utilityService.currentValue,
                onPreviousValueChange = onPreviousValueChange,
                onCurrentValueChange = onCurrentValueChange
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))
    }
}


@Composable
private fun MeterValue(
    modifier: Modifier = Modifier,
    previousValue: String,
    currentValue: String,
    onPreviousValueChange: (String) -> Unit,
    onCurrentValueChange: (String) -> Unit
) {
    var previousMeterValue by rememberSaveable { mutableStateOf(previousValue) }
    var currentMeterValue by rememberSaveable { mutableStateOf(currentValue) }

    var isError by rememberSaveable { mutableStateOf(false) }
    var isCurrentValueFocused by remember { mutableStateOf(false) }

    fun compareMaterValues() {
        val previousDigit = previousMeterValue.trimSpaces().toIntOrNull() ?: 0
        val currentDigit = currentMeterValue.trimSpaces().toIntOrNull() ?: 0
        isError = previousDigit > currentDigit
    }

    Column(
        modifier = modifier
    ) {
        Row{

            // Previous utility meter
            UtilityMeterTextField(
                modifier.weight(1f),
                value = previousMeterValue,
                onValueChange = {
                    previousMeterValue = it
                    onPreviousValueChange(it)
                    compareMaterValues()
                },
                label = "Попередні"
            )

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)))

            // Current utility meter
            UtilityMeterTextField(
                modifier
                    .weight(1f)
                    .onFocusChanged {
                        isCurrentValueFocused = it.isFocused
                    },
                value = currentMeterValue,
                onValueChange = {
                    currentMeterValue = it
                    onCurrentValueChange(it)
                    compareMaterValues()
                },
                isError = isError,
                label = "Поточні",
                imeAction = ImeAction.Done
            )
        }

        ErrorSupportingText(
            supportingText = R.string.utility_supporting_error_message,
            isEnabled = isError && isCurrentValueFocused
        )
    }

}

@DarkLightPreviews
@Composable
fun PreviewServiceItem() {
    UtilityBillsTheme {
        ServiceItem(
            utilityService = fakeUtilityService,
            onPreviousValueChange = {_, _ ->},
            onCurrentValueChange = {_, _ ->},
            onEditServiceClick = { /*TODO*/ },
            isEnabled = {}
        )
    }
}