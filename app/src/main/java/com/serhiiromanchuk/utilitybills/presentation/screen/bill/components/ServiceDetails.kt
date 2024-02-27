package com.serhiiromanchuk.utilitybills.presentation.screen.bill.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.presentation.core.components.BodyTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.ErrorSupportingText
import com.serhiiromanchuk.utilitybills.presentation.core.components.TitleTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.UtilityMeterTextField
import com.serhiiromanchuk.utilitybills.utils.addCurrencySign
import com.serhiiromanchuk.utilitybills.utils.trimSpaces

@Composable
fun ServiceDetails(
    modifier: Modifier = Modifier,
    utilityService: UtilityServiceItem,
    isChecked: Boolean,
    onPreviousValueChange: (String) -> Unit,
    onCurrentValueChange: (String) -> Unit
) {
    Column(modifier = modifier) {
        TitleTextOnSurface(text = utilityService.name)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_extra_small)))
        BodyTextOnSurface(
            text = stringResource( R.string.tariff, utilityService.tariff).addCurrencySign()
        )
        AnimatedVisibility(
            visible = utilityService.isMeterAvailable && isChecked,
            enter = fadeIn(animationSpec = tween(durationMillis = 300)),
            exit = fadeOut(animationSpec = tween(durationMillis = 100))
        ) {
            Column {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
                MeterValue(
                    previousValue = utilityService.previousValue,
                    currentValue = utilityService.currentValue,
                    onPreviousValueChange = onPreviousValueChange,
                    onCurrentValueChange = onCurrentValueChange
                )
            }
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
        Row {
            // Previous utility meter
            UtilityMeterTextField(
                modifier = Modifier.weight(1f),
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
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged { isCurrentValueFocused = it.isFocused },
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