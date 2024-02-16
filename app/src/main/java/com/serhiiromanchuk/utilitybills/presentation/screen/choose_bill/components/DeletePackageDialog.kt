package com.serhiiromanchuk.utilitybills.presentation.screen.choose_bill.components

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable

@Composable
fun DeletePackageDialog(
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { /*TODO*/ }, confirmButton = { /*TODO*/ })
}