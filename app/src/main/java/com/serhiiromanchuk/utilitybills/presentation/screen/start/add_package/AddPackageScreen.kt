package com.serhiiromanchuk.utilitybills.presentation.screen.start.add_package

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.components.TopBarApp
import com.serhiiromanchuk.utilitybills.presentation.getApplicationComponent
import com.serhiiromanchuk.utilitybills.presentation.screen.start.add_package.components.AddBillForm
import com.serhiiromanchuk.utilitybills.presentation.screen.start.add_package.components.SubmitButton

@Composable
fun AddBillScreenRoute(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    val component = getApplicationComponent()
    val viewModel: AddPackageViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                AddPackageViewModel.NavigationEvent.OnBack -> onBackPressed()
            }
        }
    }

    AddBillScreen(
        modifier = modifier,
        screenState = screenState,
        onEvent = viewModel::onEvent,
        onBackPressed = onBackPressed
    )
}

@Composable
private fun AddBillScreen(
    modifier: Modifier = Modifier,
    screenState: State<AddPackageUiState>,
    onEvent: (AddPackageUiEvent) -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            TopBarApp(
                titleId = R.string.add_new_package,
                onBackPressed = onBackPressed
            )
        },
        bottomBar = {
            SubmitButton(
                screenState = screenState.value,
                onEvent = onEvent
            )
        }
    ) { paddingValues ->
        AddBillForm(
            modifier = modifier.padding(paddingValues),
            screenState = screenState.value,
            onEvent = onEvent
        )
    }
}

