package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.BodyTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.DialogWithRectangleShape
import com.serhiiromanchuk.utilitybills.presentation.core.components.TitleTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiState.DialogState
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme

@Composable
fun DeletePackageDialog(
    dialogState: DialogState,
    closeDialog: () -> Unit,
    onConfirmClick: (Long) -> Unit,
) {
    if (dialogState is DialogState.Open) {
        DialogWithRectangleShape(
            onDismissRequest = { closeDialog() },
            confirmButton = {
                TextButton(onClick = closeDialog) {
                    Text(
                        text = stringResource(R.string.leave),
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { onConfirmClick(dialogState.id) }) {
                    Text(
                        text = stringResource(R.string.delete),
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            title = {
                TitleTextOnSurface(
                    text = stringResource(R.string.dialog_package_title),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            },
            text = {
                BodyTextOnSurface(
                    text = stringResource(R.string.dialog_package_text),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                    lineHeight = 18.sp
                )
            }
        )
    }
}

@DarkLightPreviews
@Composable
private fun DeletePackageDialogPreview() {
    UtilityBillsTheme {
        DeletePackageDialog(
            dialogState = DialogState.Open(0),
            closeDialog = {},
            onConfirmClick = {}
        )
    }
}