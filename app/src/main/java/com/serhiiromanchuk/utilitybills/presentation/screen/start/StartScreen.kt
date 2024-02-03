package com.serhiiromanchuk.utilitybills.presentation.screen.start

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.BillCardIcon
import com.serhiiromanchuk.utilitybills.presentation.core.components.CardOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.TextOnBillCard
import com.serhiiromanchuk.utilitybills.presentation.getApplicationComponent
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    loadMainScreen: () -> Unit
) {
    val component = getApplicationComponent()
    val viewModel: StartScreenViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                StartScreenViewModel.ValidationEvents.Success -> loadMainScreen()
            }
        }
    }

    Scaffold {
        StartScreenContent(
            modifier = modifier.padding(it),
            screenState = screenState.value,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun StartScreenContent(
    modifier: Modifier = Modifier,
    screenState: StartScreenUiState,
    onEvent: (StartScreenEvent) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        item {
            AddNewBill {

            }
        }
    }
}

@Composable
private fun AddNewBill(
    modifier: Modifier = Modifier,
    onAddBillClick: () -> Unit
) {
    CardOnSurface(
        modifier = modifier
            .height(170.dp),
        onCardClick = onAddBillClick
    ) {
        BillCardIcon(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            iconId = R.drawable.ic_add_folder,
            contentDescriptionId = R.string.add_bill_address,
            isAddIcon = true
        )
        TextOnBillCard(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.add_bill_address)
        )
    }
}

@Composable
private fun BillAddressCard(
    modifier: Modifier = Modifier,
    billAddress: String
) {
    CardOnSurface(
        modifier = modifier
    ) {
        BillCardIcon(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            iconId = R.drawable.ic_home,
            contentDescriptionId = null
        )
        TextOnBillCard(
            modifier = Modifier.weight(1f),
            text = billAddress
        )
    }
}

@DarkLightPreviews
@Composable
private fun AddNewBillPreview() {
    UtilityBillsTheme() {
        AddNewBill(
            onAddBillClick = {}
        )
    }
}

@DarkLightPreviews
@Composable
private fun StartScreenLayoutPreview() {
    UtilityBillsTheme() {
        StartScreenContent(
            screenState = StartScreenUiState(),
            onEvent = {}
        )
    }
}