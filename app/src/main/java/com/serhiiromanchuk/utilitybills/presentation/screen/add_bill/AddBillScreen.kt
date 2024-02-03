package com.serhiiromanchuk.utilitybills.presentation.screen.add_bill

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.components.ErrorTextMessage
import com.serhiiromanchuk.utilitybills.presentation.core.components.OutlinedTextFieldOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.PrimaryButton
import com.serhiiromanchuk.utilitybills.presentation.core.components.TitleTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.screen.start.StartScreenEvent
import com.serhiiromanchuk.utilitybills.presentation.screen.start.StartScreenUiState
import com.serhiiromanchuk.utilitybills.utils.getCreditCardTransformedText

@Composable
private fun AddBillForm(
    modifier: Modifier = Modifier,
    screenState: StartScreenUiState,
    onEvent: (StartScreenEvent) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TitleTextOnSurface(text = stringResource(R.string.enter_info_for_new_bill))

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_medium)))

        OutlinedTextFieldOnSurface(
            value = screenState.address,
            onValueChange = { onEvent(StartScreenEvent.AddressChanged(it)) },
            labelText = stringResource(R.string.address),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            isError = screenState.addressError != null
        )

        if (screenState.addressError != null) {
            ErrorTextMessage(
                modifier = Modifier.align(Alignment.Start),
                text = screenState.addressError.asString()
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))

        OutlinedTextFieldOnSurface(
            value = screenState.cardNumber,
            onValueChange = { input ->
                if (input.isDigitsOnly() && input.length <= 16) {
                    onEvent(StartScreenEvent.CardNumberChanged(input))
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            labelText = stringResource(R.string.card_number),
            isError = screenState.cardNumberError != null,
            visualTransformation = { input -> getCreditCardTransformedText(input, context) }
        )

        if (screenState.cardNumberError != null) {
            ErrorTextMessage(
                modifier = Modifier.align(Alignment.Start),
                text = screenState.cardNumberError.asString()
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_medium)))

        PrimaryButton(
            text = stringResource(R.string.save),
            onClick = { onEvent(StartScreenEvent.Submit) }
        )
    }
}