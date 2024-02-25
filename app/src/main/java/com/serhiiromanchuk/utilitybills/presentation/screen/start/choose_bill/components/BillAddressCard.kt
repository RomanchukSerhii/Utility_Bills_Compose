package com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.BillCardIcon
import com.serhiiromanchuk.utilitybills.presentation.core.components.CardOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.TextOnBillCard
import com.serhiiromanchuk.utilitybills.presentation.screen.start.choose_bill.ChooseBillState.BillCardState
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.ui.theme.editCardColor

@Composable
fun BillAddressCard(
    modifier: Modifier = Modifier,
    billAddress: String,
    cardState: BillCardState,
    elevation: Dp,
    onLongClick: () -> Unit,
    onClick: () -> Unit,
    onDeleteIconClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        when (cardState) {
            is BillCardState.EditMode -> {
                EditableBillCard(
                    modifier = Modifier.height(180.dp),
                    billAddress = billAddress,
                    elevation = elevation,
                    onDeleteIconClick = onDeleteIconClick
                )
            }
            BillCardState.Initial -> {
                RegularBillCard(
                    modifier = Modifier.height(180.dp),
                    billAddress = billAddress,
                    elevation = elevation,
                    onLongClick = onLongClick,
                    onClick = onClick
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RegularBillCard(
    modifier: Modifier = Modifier,
    billAddress: String,
    elevation: Dp,
    onLongClick: () -> Unit,
    onClick: () -> Unit,
) {
    CardOnSurface(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp),
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        elevation = elevation
    ) {
        val haptics = LocalHapticFeedback.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .combinedClickable(
                    onLongClick = {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        onLongClick()
                    },
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
                text = billAddress
            )
        }
    }
}

@Composable
fun BoxScope.EditableBillCard(
    modifier: Modifier = Modifier,
    billAddress: String,
    elevation: Dp,
    onDeleteIconClick: () -> Unit,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var isIconVisible by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        isExpanded = true
    }

    val scale by animateFloatAsState(
        targetValue = if (isExpanded) 1.05f else 1f,
        animationSpec = tween(
            durationMillis = 150,
            easing = FastOutLinearInEasing
        ),
        finishedListener = {
            if (isExpanded) isExpanded = !isExpanded
            isIconVisible = true
        },
        label = ""
    )

    CardOnSurface(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale
            ),
        containerColor = editCardColor,
        elevation = elevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable { }
        ) {
            BillCardIcon(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                iconId = R.drawable.ic_home,
                contentDescriptionId = null
            )
            TextOnBillCard(
                modifier = Modifier.weight(1f),
                text = billAddress
            )
        }
    }
        AnimatedVisibility(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.TopEnd),
            visible = isIconVisible,
            enter = scaleIn(animationSpec = tween(durationMillis = 200, delayMillis = 50))
        ) {
            IconButton(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(Color.Black)
                    .size(32.dp),
                onClick = { onDeleteIconClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.delete_bill),
                    tint = Color.White
                )
            }
        }

}

@DarkLightPreviews
@Composable
private fun AddNewBillPreview() {
    UtilityBillsTheme {
        BillAddressCard(
            billAddress = "вул. Грушевського 23, кв. 235",
            cardState = BillCardState.Initial,
            elevation = 2.dp,
            onLongClick = { },
            onClick = { },
            onDeleteIconClick = { }
        )
    }
}