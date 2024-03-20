package com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.presentation.core.components.grid.LazyDraggableVerticalGrid
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiEvent
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageUiState

@Composable
fun PackageList(
    modifier: Modifier = Modifier,
    screenState: ChoosePackageUiState,
    onEvent: (ChoosePackageUiEvent) -> Unit
) {

    LazyDraggableVerticalGrid(
        modifier = modifier.fillMaxSize(),
        items = screenState.packageList,
        key = { _, item -> item.id },
        columns = GridCells.Adaptive(minSize = 140.dp),
        onMove = { fromIndex, toIndex -> onEvent(ChoosePackageUiEvent.MovePackage(fromIndex, toIndex)) },
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_medium)),
        notDraggableContent = {
            AddNewPackage(
                isEditMode = screenState.isEditMode,
                onEvent = onEvent
            )
        },
        draggableContent = { billPackage, isDragging ->
            val elevation by animateDpAsState(if (isDragging) 4.dp else 1.dp, label = "elevation")
            PackageCard(
                billPackage = billPackage,
                cardState = screenState.packageCardState,
                elevation = elevation,
                onEvent = onEvent
            )
        }
    )
}