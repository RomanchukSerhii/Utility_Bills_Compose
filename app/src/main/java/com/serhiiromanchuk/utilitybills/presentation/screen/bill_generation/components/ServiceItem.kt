package com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.rememberRevealState
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.SwipeToRevealCard
import androidx.wear.compose.material.SwipeToRevealPrimaryAction
import androidx.wear.compose.material.SwipeToRevealSecondaryAction
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.presentation.core.components.CardOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.EditServiceIcon
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation.BillGenerationUiEvent
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation.BillGenerationUiState.ServiceItemState
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.utils.mocks.fakeUtilityService

@OptIn(ExperimentalWearMaterialApi::class, ExperimentalWearFoundationApi::class)
@Composable
fun ServiceItem(
    modifier: Modifier = Modifier,
    serviceState: ServiceItemState,
    onEvent: (BillGenerationUiEvent) -> Unit
) {
    val revealState = rememberRevealState()
    SwipeToRevealCard(
        revealState = revealState,
        primaryAction = {
            SwipeToRevealPrimaryAction(
                revealState = revealState,
                onClick = { onEvent(BillGenerationUiEvent.OpenDialog(serviceState.utilityServiceItem)) },
                icon = { Icon(imageVector = Icons.Default.Delete, contentDescription = null) },
                label = { Text(text = "Видалити") })
        },
        secondaryAction = {
            SwipeToRevealSecondaryAction(
                revealState = revealState,
                onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
        },
        onFullSwipe = { onEvent(BillGenerationUiEvent.OpenDialog(serviceState.utilityServiceItem)) }
    ) {
        CardOnSurface(
            modifier = modifier
                .animateContentSize(animationSpec = tween(durationMillis = 300))
        ) {
            Box(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                CheckedSideIndicator(checked = serviceState.isChecked)
                Row(
                    modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                    verticalAlignment = Alignment.Top
                ) {
                    val serviceItem = serviceState.utilityServiceItem
                    val isChecked = serviceState.isChecked

                    ServiceCheckBox(
                        serviceId = serviceItem.id,
                        isChecked = isChecked,
                        onEvent = onEvent
                    )

                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.width_small)))

                    ServiceDetails(
                        modifier = Modifier.weight(1f),
                        serviceState = serviceState,
                        isChecked = isChecked,
                        onEvent = onEvent
                    )

                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.width_small)))

                    EditServiceIcon(
                        modifier = Modifier.size(32.dp),
                        onEditServiceClick = {
                            onEvent(BillGenerationUiEvent.OnEditServiceClicked(serviceItem.id))
                        }
                    )
                }
            }
        }
    }

}

@Composable
private fun CheckedSideIndicator(
    modifier: Modifier = Modifier,
    checked: Boolean
) {
    val backgroundColor = if (checked) {
        MaterialTheme.colorScheme.tertiary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val width by animateDpAsState(
        animationSpec = tween(300),
        targetValue = if (checked) 4.dp else 0.dp, label = ""
    )

    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(width)
            .background(backgroundColor)
    )
}

@DarkLightPreviews
@Composable
fun PreviewServiceItem() {
    UtilityBillsTheme {
        ServiceItem(
            serviceState = ServiceItemState(fakeUtilityService),
            onEvent = { }
        )
    }
}