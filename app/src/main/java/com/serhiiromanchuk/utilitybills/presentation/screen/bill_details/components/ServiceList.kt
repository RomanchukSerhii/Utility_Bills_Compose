package com.serhiiromanchuk.utilitybills.presentation.screen.bill_details.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.model.UtilityService

@Composable
fun ServiceList(
    modifier: Modifier = Modifier,
    serviceList: List<UtilityService>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small))
    ) {
        items(serviceList, key = { it.id }) { service ->
            ServiceDetails(service = service)
        }
    }
}