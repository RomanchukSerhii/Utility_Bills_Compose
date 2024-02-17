package com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.BodyTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.DialogWithRectangleShape
import com.serhiiromanchuk.utilitybills.presentation.core.components.TitleTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill.ChooseBillState.DialogState
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
                        text = "ЗАЛИШИТИ",
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { onConfirmClick(dialogState.id) }) {
                    Text(
                        text = "ВИДАЛИТИ",
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            },
            title = {
                TitleTextOnSurface(
                    text = "Ви впевнені, що хочете видалити папку?",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
                )
            },
            text = {
                BodyTextOnSurface(
                    text = "Усі збережені в ній рахунки буде видалено",
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