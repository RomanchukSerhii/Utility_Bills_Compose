package com.serhiiromanchuk.utilitybills.presentation.screen.start.edit_package

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.components.PrimaryButton
import com.serhiiromanchuk.utilitybills.presentation.core.components.TopBarApp

@Composable
fun EditPackageScreen(
    modifier: Modifier = Modifier,
    billAddress: String,
    onBackPressed: () -> Unit
) {
//    val component = getApplicationComponent()
//        .getInsertServiceScreenComponentFactory()
//        .create(utilityServiceId, billCreatorId)
//    val viewModel: InsertUtilityServiceViewModel =
//        viewModel(factory = component.getViewModelFactory())
//    val screenState = viewModel.screenState.collectAsState()

    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            TopBarApp(
                titleId = R.string.package_name,
                onBackPressed = onBackPressed
            )
        },
        bottomBar = {
            PrimaryButton(
                modifier = modifier.fillMaxWidth(),
                text = "Продовжити",
                onClick = { onBackPressed()}
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.size(300.dp))
            Text(text = billAddress)
        }
    }
}