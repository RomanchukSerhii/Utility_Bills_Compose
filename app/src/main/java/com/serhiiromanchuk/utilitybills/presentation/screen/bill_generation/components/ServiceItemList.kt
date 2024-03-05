package com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.components.BodyTextOnSurface
import com.serhiiromanchuk.utilitybills.presentation.core.components.SwipeToDeleteContainer
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation.BillGenerationUiEvent
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation.BillGenerationUiState

@Composable
fun ServiceItemList(
    modifier: Modifier = Modifier,
    billCreatorId: Long,
    serviceStateList: List<BillGenerationUiState.ServiceItemState>,
    onEvent: (BillGenerationUiEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
    ) {
        item {
            BodyTextOnSurface(
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_small),
                    top = dimensionResource(id = R.dimen.padding_medium)
                ),
                text = stringResource(R.string.choose_utility_service_title)
            )
        }

        items(serviceStateList, key = {
            it.utilityServiceItem.id
        }) { serviceState ->
            SwipeToDeleteContainer(item = serviceState, onDelete = {
                onEvent(BillGenerationUiEvent.OpenDialog(it.utilityServiceItem))
            }) {
                ServiceItem(serviceState = serviceState, onEvent = onEvent)
            }

        }

        item {
            AddUtilityServiceCard(
                modifier = Modifier.clickable {
                    onEvent(BillGenerationUiEvent.AddUtilityService(billCreatorId))
                }
            )
        }
    }
}