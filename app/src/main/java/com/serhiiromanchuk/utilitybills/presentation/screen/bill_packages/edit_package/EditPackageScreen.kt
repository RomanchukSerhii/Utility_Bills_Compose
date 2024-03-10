package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.edit_package

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.components.PrimaryButton
import com.serhiiromanchuk.utilitybills.presentation.core.components.TopBarApp
import com.serhiiromanchuk.utilitybills.presentation.getApplicationComponent
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.edit_package.componnents.PackageNameTextField

@Composable
fun EditPackageScreenRoot(
    modifier: Modifier = Modifier,
    billId: Long,
    billAddress: String,
    onBackPressed: () -> Unit
) {
    val component = getApplicationComponent()
        .getEditPackageScreenComponentFactory()
        .create(billAddress, billId)
    val viewModel: EditPackageViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsStateWithLifecycle()

    EditPackageScreen(
        modifier = modifier,
        screenState = screenState,
        onEvent = viewModel::onEvent,
        onBackPressed = onBackPressed
    )
}

@Composable
fun EditPackageScreen(
    modifier: Modifier = Modifier,
    screenState: State<EditPackageScreenState>,
    onEvent: (EditPackageScreenEvent) -> Unit,
    onBackPressed: () -> Unit
) {
    val currentState = screenState.value

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            TopBarApp(
                titleId = R.string.package_name,
                onBackPressed = onBackPressed
            )
        },
        bottomBar = {
            PrimaryButton(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                text = stringResource(R.string.continue_button),
                onClick = {
                    onEvent(EditPackageScreenEvent.Submit)
                    onBackPressed()
                },
                enabled = currentState.isSubmitButtonEnable
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            PackageNameTextField(currentState = currentState, onEvent = onEvent)
        }
    }
}