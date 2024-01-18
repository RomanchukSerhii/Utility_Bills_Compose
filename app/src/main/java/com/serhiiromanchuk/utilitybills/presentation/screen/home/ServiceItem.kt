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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
    previousValueChange: (Int) -> Unit,
    currentValueChange: (Int) -> Unit,
    onEditServiceClick: () -> Unit,
    isEnabled: (Boolean) -> Unit
) {
    var isChecked by remember { mutableStateOf(checked) }
    Card(
        modifier = modifier.animateContentSize(
            animationSpec = tween(durationMillis = 800)
        ).padding(4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            CheckedIndicator(checked = isChecked)
            ServiceItemContent(
                utilityService = utilityService,
                checked = isChecked,
                previousValueChange = previousValueChange,
                currentValueChange = currentValueChange,
                onEditServiceClick = onEditServiceClick,
                isEnabled = {
                    isChecked = it
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
    previousValueChange: (Int) -> Unit,
    currentValueChange: (Int) -> Unit,
    onEditServiceClick: () -> Unit,
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
            previousValueChange = previousValueChange,
            currentValueChange = currentValueChange
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.width_small)))
        EditServiceIcon(onEditServiceClick = onEditServiceClick)
    }
}

@Composable
private fun ServiceDetails(
    modifier: Modifier = Modifier,
    utilityService: UtilityServiceItem,
    isChecked: Boolean,
    previousValueChange: (Int) -> Unit,
    currentValueChange: (Int) -> Unit
) {
    Column(modifier = modifier) {
        TitleTextOnSurface(text = utilityService.name)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_extra_small)))
        BodyTextOnSurface(text = stringResource(R.string.tariff, utilityService.tariff))
        if (utilityService.isMeterAvailable && isChecked) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
            MeterValue(
                utilityService = utilityService,
                previousValueChange = previousValueChange,
                currentValueChange = currentValueChange
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))
    }
}


@Composable
private fun MeterValue(
    modifier: Modifier = Modifier,
    utilityService: UtilityServiceItem,
    previousValueChange: (Int) -> Unit,
    currentValueChange: (Int) -> Unit
) {
    var previousValue by rememberSaveable {
        mutableStateOf(utilityService.previousValue.toString())
    }
    var currentValue by rememberSaveable {
        mutableStateOf(utilityService.currentValue.toString())
    }

    var isError by rememberSaveable { mutableStateOf(false) }
    var isPreviousValueFocused by remember { mutableStateOf(false) }

    fun compareMaterValues() {
        val previousDigit = previousValue.trimSpaces().toIntOrNull() ?: 0
        val currentDigit = currentValue.trimSpaces().toIntOrNull() ?: 0
        isError = previousDigit > currentDigit
    }

    Column(
        modifier = modifier
    ) {
        Row{
            UtilityMeterTextField(
                modifier.weight(1f),
                value = previousValue,
                onValueChange = {
                    previousValue = it
                    compareMaterValues()
                },
                label = "Попередні"
            )
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)))
            UtilityMeterTextField(
                modifier
                    .weight(1f)
                    .onFocusChanged {
                        isPreviousValueFocused = it.isFocused
                    },
                value = currentValue,
                onValueChange = {
                    currentValue = it
                    compareMaterValues()
                },
                isError = isError,
                label = "Поточні",
                imeAction = ImeAction.Done
            )
        }

        ErrorSupportingText(
            supportingText = R.string.utility_supporting_error_message,
            isEnabled = isError && isPreviousValueFocused
        )
    }

}

@DarkLightPreviews
@Composable
fun PreviewServiceItem() {
    UtilityBillsTheme {
        ServiceItem(
            utilityService = fakeUtilityService,
            previousValueChange = {},
            currentValueChange = {},
            onEditServiceClick = { /*TODO*/ },
            isEnabled = {}
        )
    }
}