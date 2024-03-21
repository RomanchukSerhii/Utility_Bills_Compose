package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.components

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
import com.serhiiromanchuk.utilitybills.domain.model.BillPackage
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.CardOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.PackageCardIcon
import com.serhiiromanchuk.utilitybills.presentation.core.components.TextOnPackageCard
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiEvent
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiState.PackageCardState
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.ui.theme.editCardColor

@Composable
fun PackageCard(
    modifier: Modifier = Modifier,
    billPackage: BillPackage,
    cardState: PackageCardState,
    elevation: Dp,
    onEvent: (ChoosePackageUiEvent) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        when (cardState) {
            is PackageCardState.EditMode -> {
                EditablePackageCard(
                    modifier = Modifier.height(180.dp),
                    packageName = billPackage.name,
                    elevation = elevation,
                    onDeleteIconClick = { onEvent(ChoosePackageUiEvent.OpenDialog(billPackage.id)) }
                )
            }

            PackageCardState.Initial -> {
                RegularPackageCard(
                    modifier = Modifier.height(180.dp),
                    packageName = billPackage.name,
                    elevation = elevation,
                    onLongClick = {
                        onEvent(
                            ChoosePackageUiEvent.OpenBottomSheet(
                                billPackage.name,
                                billPackage.id
                            )
                        )
                    },
                    onClick = { onEvent(ChoosePackageUiEvent.PackageClicked(billPackage.id)) }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RegularPackageCard(
    modifier: Modifier = Modifier,
    packageName: String,
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
            PackageCardIcon(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                iconId = R.drawable.ic_home,
                contentDescriptionId = null
            )
            TextOnPackageCard(
                modifier = Modifier.weight(1f),
                text = packageName
            )
        }
    }
}

@Composable
fun BoxScope.EditablePackageCard(
    modifier: Modifier = Modifier,
    packageName: String,
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
            PackageCardIcon(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                iconId = R.drawable.ic_home,
                contentDescriptionId = null
            )
            TextOnPackageCard(
                modifier = Modifier.weight(1f),
                text = packageName
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
                contentDescription = stringResource(R.string.delete_bill_package),
                tint = Color.White
            )
        }
    }

}

@DarkLightPreviews
@Composable
private fun AddPackagePreview() {
    UtilityBillsTheme {
        PackageCard(
            billPackage = BillPackage(
                name = "вул. Грушевського 23, кв. 235",
                payerName = "",
                address = ""
            ),
            cardState = PackageCardState.Initial,
            elevation = 2.dp,
            onEvent = {}
        )
    }
}