package com.serhiiromanchuk.utilitybills.presentation.screen.start.add_bill.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.PrimaryButton
import com.serhiiromanchuk.utilitybills.presentation.screen.start.add_bill.AddBillScreenEvent
import com.serhiiromanchuk.utilitybills.presentation.screen.start.add_bill.AddBillScreenState
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme

@Composable
fun SubmitButton(
    screenState: AddBillScreenState,
    onClick: (AddBillScreenEvent) -> Unit
) {
    val building = if (screenState.building.isNotEmpty()) {
        stringResource(R.string.building_value, screenState.building.trim())
    } else ""

    val apartment = if (screenState.apartment.isNotEmpty()) {
        stringResource(R.string.apartment_value, screenState.apartment.trim())
    } else ""

    val address = stringResource(
        R.string.address_value,
        screenState.street.trim(),
        screenState.house.trim(),
        building,
        apartment
    )

    PrimaryButton(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
        text = stringResource(R.string.next),
        onClick = { onClick(AddBillScreenEvent.Submit(address)) },
        enabled = screenState.nextButtonAvailable
    )
}

@DarkLightPreviews
@Composable
private fun SubmitButtonPreview() {
    UtilityBillsTheme() {
        SubmitButton(screenState = AddBillScreenState(
        ), onClick = {})
    }
}