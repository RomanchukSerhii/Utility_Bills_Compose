package com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.mocks.fakeBillItem
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.TopBarApp
import com.serhiiromanchuk.utilitybills.presentation.getApplicationComponent
import com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill.components.BillAddressList
import com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill.components.DeletePackageDialog
import com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill.components.SettingsBottomSheet
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.ui.theme.editModeBackground

@Composable
fun ChooseBillScreenRoot(
    modifier: Modifier = Modifier,
    onAddBillClick: () -> Unit,
    onBillItemClick: (Long) -> Unit,
    onEditPackageClick: (String, Long) -> Unit
) {
    val component = getApplicationComponent()
    val viewModel: ChooseBillViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsStateWithLifecycle()

    ChooseBillScreen(
        modifier = modifier,
        screenState = screenState,
        onAddBillClick = onAddBillClick,
        onBillItemClick = onBillItemClick,
        onEditPackageClick = onEditPackageClick,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun ChooseBillScreen(
    modifier: Modifier = Modifier,
    screenState: State<ChooseBillState>,
    onAddBillClick: () -> Unit,
    onBillItemClick: (Long) -> Unit,
    onEditPackageClick: (String, Long) -> Unit,
    onEvent: (ChooseBillEvent) -> Unit
) {
    val currentState = screenState.value

    Scaffold(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                if (screenState.value.isEditMode) { onEvent(ChooseBillEvent.ChangeEditMode) }
            },
        topBar = { TopBarApp(titleId = R.string.utility_bills) },
        containerColor = if (screenState.value.isEditMode) {
            editModeBackground
        } else {
            MaterialTheme.colorScheme.background
        }
    ) { paddingValues ->

        BillAddressList(
            modifier = Modifier.padding(paddingValues),
            screenState = currentState,
            onEvent = onEvent,
            onAddBillClick = onAddBillClick,
            onBillItemClick = onBillItemClick
        )

        DeletePackageDialog(
            dialogState = currentState.dialogState,
            closeDialog = { onEvent(ChooseBillEvent.CloseDialog) },
            onConfirmClick = { onEvent(ChooseBillEvent.DeleteBill(it)) }
        )

        SettingsBottomSheet(
            visibleState = currentState.visibleSheetState,
            onDismissRequest = { onEvent(ChooseBillEvent.CloseBottomSheet) },
            onChangeNameClick = { address, id ->
                onEvent(ChooseBillEvent.SetInitialState)
                onEditPackageClick(address, id)
            },
            onEditModeClick = { onEvent(ChooseBillEvent.ChangeEditMode) }
        )
    }
}

@DarkLightPreviews
@Composable
private fun ChooseBillScreenPreview() {
    UtilityBillsTheme {
        val mockState = ChooseBillState(
                listOf(
                    fakeBillItem,
                    fakeBillItem.copy(
                        id = 1,
                        address = "вул. Коцюбинського 34, кв. 15"
                    ),
                    fakeBillItem.copy(
                        id = 2,
                        address = "вул. Пилипа Орлика 14, кв.3"
                    )
                )
        )
        ChooseBillScreen(
            screenState = mutableStateOf(mockState),
            onAddBillClick = {},
            onBillItemClick = {},
            onEditPackageClick = { _, _ ->},
            onEvent = {}
        )
    }
}