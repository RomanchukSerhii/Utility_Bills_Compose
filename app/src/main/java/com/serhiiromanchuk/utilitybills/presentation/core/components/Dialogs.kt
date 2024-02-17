package com.serhiiromanchuk.utilitybills.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.serhiiromanchuk.utilitybills.R

@Composable
fun DialogWithRectangleShape(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    dismissButton: (@Composable () -> Unit)? = null,
    properties: DialogProperties = DialogProperties(
        usePlatformDefaultWidth = false
    ),
    title: (@Composable () -> Unit)? = null,
    text: (@Composable () -> Unit)? = null,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 20.dp,
                    bottom = 8.dp
                )
        ) {
            title?.let { title() }
            Spacer(modifier = Modifier.height(12.dp))
            text?.let { text() }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.align(Alignment.End)
            ) {
                confirmButton()
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.width_small)))
                dismissButton?.let { dismissButton() }
            }
        }
    }
}