package com.serhiiromanchuk.utilitybills.presentation.screen.bill_details

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
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.components.TopBarApp
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_details.components.BillCard

@Composable
fun BillScreen(
    modifier: Modifier = Modifier,
    screenState: State<BillUiState>,
    onEvent: (BillUiEvent) -> Unit
) {
    val currentState = screenState.value
    Scaffold(
        modifier = modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            TopBarApp(
                titleId = R.string.bill,
                onBackPressed = { onEvent(BillUiEvent.OnBackClicked) }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BillCard(billDescription = currentState.billDescription, onEvent = onEvent)
        }
    }
}