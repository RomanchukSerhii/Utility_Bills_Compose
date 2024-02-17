package com.serhiiromanchuk.utilitybills.presentation.screen.start.add_bill

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.components.TopBarApp
import com.serhiiromanchuk.utilitybills.presentation.getApplicationComponent
import com.serhiiromanchuk.utilitybills.presentation.screen.start.add_bill.components.AddBillForm
import com.serhiiromanchuk.utilitybills.presentation.screen.start.add_bill.components.SubmitButton

@Composable
fun AddBillScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    val component = getApplicationComponent()
    val viewModel: AddBillViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsState()

    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            TopBarApp(
                titleId = R.string.add_bill_address,
                onBackPressed = onBackPressed
            )
        },
        bottomBar = {
            SubmitButton(
                screenState = screenState.value,
                onClick = {
                    viewModel.onEvent(it)
                    onBackPressed()
                }
            )
        }
    ) {
        AddBillForm(
            modifier = modifier.padding(it),
            screenState = screenState.value,
            onEvent = { event -> viewModel.onEvent(event) }
        )
    }
}

