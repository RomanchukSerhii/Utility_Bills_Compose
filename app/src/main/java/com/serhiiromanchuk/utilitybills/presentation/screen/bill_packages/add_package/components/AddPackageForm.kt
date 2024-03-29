package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.TitleTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package.AddPackageUiEvent
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package.AddPackageUiState
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme

@Composable
fun AddPackageForm(
    modifier: Modifier = Modifier,
    screenState: AddPackageUiState,
    onEvent: (AddPackageUiEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        TitleTextOnSurface(
            modifier = Modifier.padding(vertical = 8.dp),
            text = stringResource(R.string.enter_info_for_new_bill)
        )

        Column {
            AddressTextField(
                value = screenState.payerName,
                onValueChange = { onEvent(AddPackageUiEvent.PayerNameChanged(it))},
                label = stringResource(R.string.payer_name)
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_medium)))
            
            AddressTextField(
                value = screenState.city,
                onValueChange = { onEvent(AddPackageUiEvent.CityChanged(it))},
                label = stringResource(R.string.city_name)
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_medium)))

            AddressTextField(
                value = screenState.street,
                onValueChange = { onEvent(AddPackageUiEvent.StreetChanged(it))},
                label = stringResource(R.string.street)
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_medium)))

            Row {
                HouseTextField(value = screenState.house, onEvent = onEvent)

                Spacer(modifier = Modifier.width(12.dp))

                BuildingTextField(value = screenState.building, onEvent = onEvent)

                Spacer(modifier = Modifier.width(12.dp))

                ApartmentTextField(value = screenState.apartment, onEvent = onEvent)
            }
        }
    }
}

@DarkLightPreviews
@Composable
private fun AddPackageFormPreview() {
    UtilityBillsTheme {
        AddPackageForm(
            screenState = AddPackageUiState(),
            onEvent = {}
        )
    }
}