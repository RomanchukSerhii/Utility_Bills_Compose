package com.serhiiromanchuk.utilitybills.presentation.screen.start.edit_package.componnents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
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
//    val direction = LocalLayoutDirection.current
    val focusRequester = remember { FocusRequester() }

    DisposableEffect(key1 = true) {
        focusRequester.requestFocus()
        onDispose { }
    }
    BasicTextField(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_large))
            .fillMaxWidth()
            .wrapContentHeight()
            .focusRequester(focusRequester),
        value = TextFieldValue(
            text = currentState.address,
            selection = currentState.addressSelection
        ),
        onValueChange = { onEvent(EditPackageScreenEvent.AddressChanged(it.text, it.selection))},
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            lineHeight = 36.sp
        )
    )


}