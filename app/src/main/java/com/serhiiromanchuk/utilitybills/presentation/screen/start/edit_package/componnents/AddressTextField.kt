package com.serhiiromanchuk.utilitybills.presentation.screen.start.edit_package.componnents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.screen.start.edit_package.EditPackageScreenEvent
import com.serhiiromanchuk.utilitybills.presentation.screen.start.edit_package.EditPackageScreenState

@Composable
fun AddressTextField(
    currentState: EditPackageScreenState,
    onEvent: (EditPackageScreenEvent) -> Unit
) {
    // Set cursor to the end of string
    val direction = LocalLayoutDirection.current
    val focusRequester = remember { FocusRequester() }

    DisposableEffect(key1 = true) {
        focusRequester.requestFocus()
        onDispose { }
    }

    BasicTextField(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_large))
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = TextFieldValue(
            text = currentState.address,
            selection = if (direction == LayoutDirection.Ltr) {
                TextRange(currentState.address.length)
            } else TextRange.Zero
        ),
        onValueChange = { onEvent(EditPackageScreenEvent.AddressChanged(it.text)) },
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            lineHeight = 36.sp
        )
    )
}