package com.serhiiromanchuk.utilitybills.presentation.screen.insert_service

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.components.PrimaryButton
import com.serhiiromanchuk.utilitybills.presentation.core.components.TopBarApp
import com.serhiiromanchuk.utilitybills.presentation.getApplicationComponent
import com.serhiiromanchuk.utilitybills.presentation.screen.insert_service.components.InsertServiceForm

@Composable
fun InsertServiceScreenRoot(
    modifier: Modifier = Modifier,
    utilityServiceId: Long,
    billCreatorId: Long,
    onBackPressed: () -> Unit
) {
    val component = getApplicationComponent()
        .getInsertServiceScreenComponentFactory()
        .create(utilityServiceId, billCreatorId)
    val viewModel: InsertServiceViewModel =
        viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.navigationEvents.collect { event ->
            when (event) {
                InsertServiceViewModel.NavigationEvent.OnBack -> onBackPressed()
            }
        }
    }

    InsertServiceScreen(
        modifier = modifier,
        utilityServiceId = utilityServiceId,
        screenState = screenState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun InsertServiceScreen(
    modifier: Modifier = Modifier,
    utilityServiceId: Long,
    screenState: State<InsertServiceUiState>,
    onEvent: (InsertServiceUiEvent) -> Unit
) {
    val currentState = screenState.value
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            TopBarApp(
                titleId = if (utilityServiceId > 0) {
                    R.string.insert_service_title
                } else {
                    R.string.add_service_title
                },
                onBackPressed = { onEvent(InsertServiceUiEvent.OnBackClicked) }
            )
        },
        bottomBar = {
            PrimaryButton(
                modifier = modifier.fillMaxWidth(),
                text = "Зберегти",
                onClick = { onEvent(InsertServiceUiEvent.Submit) },
                enabled = currentState.isSaveButtonAvailable
            )
        }
    ) {
        InsertServiceForm(
            modifier = modifier.padding(it),
            screenState = screenState.value,
            onEvent = onEvent
        )
    }
}



