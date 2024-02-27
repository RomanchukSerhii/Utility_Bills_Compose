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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.components.BodyTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.ErrorSupportingText
import com.serhiiromanchuk.utilitybills.presentation.core.components.TitleTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.UtilityMeterTextField
import com.serhiiromanchuk.utilitybills.presentation.screen.bill.BillUiEvent
import com.serhiiromanchuk.utilitybills.presentation.screen.bill.BillUiState.ServiceItemState
import com.serhiiromanchuk.utilitybills.utils.addCurrencySign

@Composable
fun ServiceDetails(
    modifier: Modifier = Modifier,
    serviceState: ServiceItemState,
    isChecked: Boolean,
    onEvent: (BillUiEvent) -> Unit
) {
    val utilityService = serviceState.utilityServiceItem
    Column(modifier = modifier) {
        TitleTextOnSurface(text = utilityService.name)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_extra_small)))
        BodyTextOnSurface(
            text = stringResource(R.string.tariff, utilityService.tariff).addCurrencySign()
        )
        AnimatedVisibility(
            visible = utilityService.isMeterAvailable && isChecked,
            enter = fadeIn(animationSpec = tween(durationMillis = 300)),
            exit = fadeOut(animationSpec = tween(durationMillis = 100))
        ) {
            Column {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
                MeterValue(
                    serviceState = serviceState,
                    onEvent = onEvent
                )
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))
    }
}


@Composable
private fun MeterValue(
    modifier: Modifier = Modifier,
    serviceState: ServiceItemState,
    onEvent: (BillUiEvent) -> Unit
) {
    val serviceItem = serviceState.utilityServiceItem
    var isCurrentValueFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        Row {
            // Previous utility meter
            UtilityMeterTextField(
                modifier = Modifier.weight(1f),
                value = serviceItem.previousValue,
                onValueChange = { previous ->
                    onEvent(BillUiEvent.PreviousValueChanged(serviceItem.id, previous))
                },
                label = "Попередні"
            )

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)))

            // Current utility meter
            UtilityMeterTextField(
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged { isCurrentValueFocused = it.isFocused },
                value = serviceItem.currentValue,
                onValueChange = { current ->
                    onEvent(BillUiEvent.CurrentValueChanged(serviceItem.id, current))
                },
                isError = serviceState.currentTextFieldError,
                label = "Поточні",
                imeAction = ImeAction.Done
            )
        }

        ErrorSupportingText(
            supportingText = R.string.utility_supporting_error_message,
            isEnabled = serviceState.currentTextFieldError && isCurrentValueFocused
        )
    }
}