package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package

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
import com.serhiiromanchuk.utilitybills.presentation.navigation.NavigationState
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package.components.AddPackageForm
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package.components.SubmitButton

@Composable
fun AddPackageScreenRoot(
    modifier: Modifier = Modifier,
    navigationState: NavigationState
) {
    val component = getApplicationComponent()
    val viewModel: AddPackageViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                AddPackageNavigationEvent.BackClicked -> navigationState.navHostController.popBackStack()
            }
        }
    }

    AddPackageScreen(
        modifier = modifier,
        screenState = screenState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun AddPackageScreen(
    modifier: Modifier = Modifier,
    screenState: State<AddPackageUiState>,
    onEvent: (AddPackageUiEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            TopBarApp(
                titleId = R.string.add_new_package,
                onBackPressed = { onEvent(AddPackageUiEvent.BackClicked) }
            )
        },
        bottomBar = {
            SubmitButton(
                screenState = screenState.value,
                onEvent = onEvent
            )
        }
    ) { paddingValues ->
        AddPackageForm(
            modifier = modifier.padding(paddingValues),
            screenState = screenState.value,
            onEvent = onEvent
        )
    }
}

