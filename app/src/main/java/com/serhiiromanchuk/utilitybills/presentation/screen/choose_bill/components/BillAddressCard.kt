package com.serhiiromanchuk.utilitybills.presentation.screen.choose_bill.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.mocks.fakeBillItem
import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.BillCardIcon
import com.serhiiromanchuk.utilitybills.presentation.core.components.CardOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.TextOnBillCard
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.ui.theme.editCardColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BillAddressCard(
    modifier: Modifier = Modifier,
    bill: BillItem,
    editMode: Boolean,
    onLongClick: () -> Unit,
    onClick: () -> Unit,
    onDeleteIconClick: (Long) -> Unit
) {
    Box(
        modifier = modifier,
    ) {
        CardOnSurface(
            modifier = modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_extra_small)),
            containerColor = if (editMode) {
                editCardColor
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .combinedClickable(
                        onLongClick = onLongClick,
                        onClick = onClick
                    )
            ) {
                BillCardIcon(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    iconId = R.drawable.ic_home,
                    contentDescriptionId = null
                )
                TextOnBillCard(
                    modifier = Modifier.weight(1f),
                    text = bill.address
                )
            }
        }

        if (editMode) {
            IconButton(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(Color.Black)
                    .size(32.dp)
                    .align(Alignment.TopEnd),
                onClick = { onDeleteIconClick(bill.id) }
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.delete_bill),
                    tint = Color.White
                )
            }
        }

    }
}

@DarkLightPreviews
@Composable
private fun AddNewBillPreview() {
    UtilityBillsTheme {
        BillAddressCard(
            bill = fakeBillItem,
            editMode = false,
            onLongClick = { },
            onClick = { },
            onDeleteIconClick = { }
        )
    }
}