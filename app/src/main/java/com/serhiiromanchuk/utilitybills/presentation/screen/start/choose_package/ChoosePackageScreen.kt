package com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_package

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
import com.serhiiromanchuk.utilitybills.domain.model.BillPackage
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.TopBarApp
import com.serhiiromanchuk.utilitybills.presentation.getApplicationComponent
import com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_package.components.DeletePackageDialog
import com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_package.components.PackageList
import com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_package.components.SettingsBottomSheet
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.ui.theme.editModeBackground

@Composable
fun ChoosePackageScreenRoot(
    modifier: Modifier = Modifier,
    onAddBillClick: () -> Unit,
    onBillItemClick: (Long) -> Unit,
    onEditPackageClick: (String, Long) -> Unit
) {
    val component = getApplicationComponent()
    val viewModel: ChoosePackageViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsStateWithLifecycle()

    ChoosePackageScreen(
        modifier = modifier,
        screenState = screenState,
        onAddBillClick = onAddBillClick,
        onBillItemClick = onBillItemClick,
        onEditPackageClick = onEditPackageClick,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun ChoosePackageScreen(
    modifier: Modifier = Modifier,
    screenState: State<ChoosePackageState>,
    onAddBillClick: () -> Unit,
    onBillItemClick: (Long) -> Unit,
    onEditPackageClick: (String, Long) -> Unit,
    onEvent: (ChoosePackageEvent) -> Unit
) {
    val currentState = screenState.value

    Scaffold(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                if (screenState.value.isEditMode) { onEvent(ChoosePackageEvent.ChangeEditMode) }
            },
        topBar = { TopBarApp(titleId = R.string.utility_bills) },
        containerColor = if (screenState.value.isEditMode) {
            editModeBackground
        } else {
            MaterialTheme.colorScheme.background
        }
    ) { paddingValues ->

        PackageList(
            modifier = Modifier.padding(paddingValues),
            screenState = currentState,
            onEvent = onEvent,
            onAddPackageClick = onAddBillClick,
            onPackageClick = onBillItemClick
        )

        DeletePackageDialog(
            dialogState = currentState.dialogState,
            closeDialog = { onEvent(ChoosePackageEvent.CloseDialog) },
            onConfirmClick = { onEvent(ChoosePackageEvent.DeletePackage(it)) }
        )

        SettingsBottomSheet(
            visibleState = currentState.visibleSheetState,
            onDismissRequest = { onEvent(ChoosePackageEvent.CloseBottomSheet) },
            onChangeNameClick = { address, id ->
                onEvent(ChoosePackageEvent.SetInitialState)
                onEditPackageClick(address, id)
            },
            onEditModeClick = { onEvent(ChoosePackageEvent.ChangeEditMode) }
        )
    }
}

@DarkLightPreviews
@Composable
private fun ChoosePackageScreenPreview() {
    UtilityBillsTheme {
        val mockState = ChoosePackageState(
                listOf(
                    BillPackage(
                        id = 1,
                        name = "вул. Коцюбинського 34, кв. 15",
                        indexPosition = 1
                    ),
                    BillPackage(
                        id = 2,
                        name = "вул. Пилипа Орлика 14, кв.3",
                        indexPosition = 2
                    )
                )
        )
        ChoosePackageScreen(
            screenState = mutableStateOf(mockState),
            onAddBillClick = {},
            onBillItemClick = {},
            onEditPackageClick = { _, _ ->},
            onEvent = {}
        )
    }
}