package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.CardOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.PackageCardIcon
import com.serhiiromanchuk.utilitybills.presentation.core.components.TextOnPackageCard
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiEvent
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddNewPackage(
    modifier: Modifier = Modifier,
    isEditMode: Boolean,
    onEvent: (ChoosePackageUiEvent) -> Unit
) {
    CardOnSurface(
        modifier = modifier
            .fillMaxSize()
            .height(180.dp)
            .padding(dimensionResource(id = R.dimen.padding_extra_small))
    ) {
        Column(
            modifier = Modifier
                .combinedClickable(
                    onLongClick = { },
                    onClick = { if (!isEditMode) onEvent(ChoosePackageUiEvent.ClickAddBill) }
                )
        ) {
            PackageCardIcon(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                iconId = R.drawable.ic_add_folder,
                contentDescriptionId = R.string.add_new_package,
                isAddIcon = true
            )
            TextOnPackageCard(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.add_new_package)
            )
        }
    }
}

@DarkLightPreviews
@Composable
private fun AddNewPackagePreview() {
    UtilityBillsTheme {
        AddNewPackage(isEditMode = false, onEvent = {})
    }
}