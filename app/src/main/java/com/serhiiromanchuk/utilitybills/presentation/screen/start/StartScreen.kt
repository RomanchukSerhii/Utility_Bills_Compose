package com.serhiiromanchuk.utilitybills.presentation.screen.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.components.OutlinedTextFieldOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.PrimaryButton
import com.serhiiromanchuk.utilitybills.presentation.core.components.TitleTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.formatToCardNumberType
import com.serhiiromanchuk.utilitybills.presentation.screen.start.StartScreenUiState.*

@Composable
fun StartScreenLayout(
    modifier: Modifier = Modifier,
    startScreenUiState: StartScreenUiState,
    onSaveButtonClick: (address: String, cardNumber: String) -> Unit,
    skipStartScreen: () -> Unit
) {
    var address by rememberSaveable { mutableStateOf("") }
    var cardNumber by rememberSaveable { mutableStateOf("") }

    var isAddressFieldEmpty by rememberSaveable { mutableStateOf(false) }
    var isCardNumberFieldEmpty by rememberSaveable { mutableStateOf(false) }
    var isCardNumberNotValid by rememberSaveable { mutableStateOf(false) }

    when (startScreenUiState) {
        is Initial -> { }
        is LoadingMainScreen -> { skipStartScreen() }
        is Error -> {
            isAddressFieldEmpty = startScreenUiState.isAddressFieldEmpty
            isCardNumberFieldEmpty = startScreenUiState.isCardNumberFieldEmpty
            isCardNumberNotValid = startScreenUiState.isCardNumberNotValid
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TitleTextOnSurface(text = stringResource(R.string.enter_info_for_new_bill))

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_medium)))

        OutlinedTextFieldOnSurface(
            value = address,
            onValueChange = { address = it.trim() },
            labelText = stringResource(R.string.address),
            isError = isAddressFieldEmpty
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))

        OutlinedTextFieldOnSurface(
            value = cardNumber,
            onValueChange = { cardNumber = formatToCardNumberType(it.trim()) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            labelText = stringResource(R.string.card_number),
            isError = isCardNumberFieldEmpty || isCardNumberNotValid
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_medium)))

        PrimaryButton(
            text = stringResource(R.string.save),
            onClick = { onSaveButtonClick(address, cardNumber) }
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun StartScreenLayoutPreview() {
    UtilityBillsTheme(darkTheme = false) {
        StartScreenLayout(
            modifier = Modifier.padding(16.dp),
            startScreenUiState = Initial,
            onSaveButtonClick = { address, cardNumber ->  },
            skipStartScreen = {}
        )
    }
}