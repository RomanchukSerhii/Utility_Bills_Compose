package com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.TitleTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill.ChooseBillState.VisibleSheetState
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheet(
    visibleState: VisibleSheetState,
    onDismissRequest: () -> Unit,
    onChangeNameClick: (String, Long) -> Unit,
    onEditModeClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    if (visibleState is VisibleSheetState.Open) {
        ModalBottomSheet(
            modifier = Modifier.navigationBarsPadding(),
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            windowInsets = WindowInsets.ime,
            containerColor = MaterialTheme.colorScheme.background,
            dragHandle = {},
            shape = MaterialTheme.shapes.medium
        ) {
            BottomSheetContent(
                billId = visibleState.billId,
                billAddress = visibleState.billAddress,
                onChangeNameClick = onChangeNameClick,
                onEditModeClick = onEditModeClick
            )
        }
    }
}

@Composable
private fun BottomSheetContent(
    billId: Long,
    billAddress: String,
    onChangeNameClick: (String, Long) -> Unit,
    onEditModeClick: () -> Unit
) {
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Column(
        modifier = Modifier
            .padding(bottom = bottomPadding)
            .fillMaxWidth()
    ) {
        BottomSheetHeader()

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_medium)))

        EditPackageButton(
            billId = billId,
            billAddress = billAddress,
            onClick = onChangeNameClick
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_small)))

        MoveOrDeleteButton(onClick = onEditModeClick)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_medium)))
    }
}

@Composable
private fun BottomSheetHeader() {
    TitleTextOnSurface(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
        text = stringResource(R.string.package_settings),
    )

    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
    )
}

@Composable
private fun EditPackageButton(
    billId: Long,
    billAddress: String,
    onClick: (String, Long) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick(billAddress, billId) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .minimumInteractiveComponentSize()
                .size(22.dp),
            painter = painterResource(id = R.drawable.ic_edit),
            contentDescription = stringResource(R.string.change_package_name),
            tint = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.width_medium)))
        Text(
            text = stringResource(R.string.change_name),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun MoveOrDeleteButton(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .minimumInteractiveComponentSize()
                .size(22.dp),
            painter = painterResource(id = R.drawable.ic_change),
            contentDescription = stringResource(R.string.change_package_name),
            tint = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.width_medium)))
        Text(
            text = stringResource(R.string.move_or_delete),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@DarkLightPreviews
@Composable
private fun BottomSheetContentPreview() {
    UtilityBillsTheme {
        BottomSheetContent(
            billId = 1,
            billAddress = "",
            onChangeNameClick = { _, _ -> },
            onEditModeClick = {}
        )
    }
}