package com.serhiiromanchuk.utilitybills.presentation.core.components.grid

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : Any> LazyDraggableVerticalGrid(
    modifier: Modifier = Modifier,
    items: List<T>,
    key: ((index: Int, item: T) -> Any)? = null,
    columns: GridCells,
    onMove: (Int, Int) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    notDraggableContent: @Composable () -> Unit,
    draggableContent: @Composable (T, Boolean) -> Unit
) {
    val gridState = rememberLazyGridState()
    val dragDropState = rememberGridDragDropState(gridState, onMove)

    val haptics = LocalHapticFeedback.current

    LazyVerticalGrid(
        columns = columns,
        modifier = modifier.dragContainer(dragDropState, haptics),
        state = gridState,
        contentPadding = contentPadding
    ) {
        itemsIndexed(items, key = key) { index, item ->
            DraggableItem(dragDropState, index) {isDragging ->
                draggableContent(item, isDragging)
            }
        }
        item(key = LazyDraggableGridState.KEY_IGNORE) {
            notDraggableContent()
        }
    }
}

fun Modifier.dragContainer(dragDropState: LazyDraggableGridState, haptics: HapticFeedback): Modifier {
    return pointerInput(dragDropState) {
        detectDragGesturesAfterLongPress(
            onDrag = { change, offset ->
                change.consume()
                dragDropState.onDrag(offset = offset)
            },
            onDragStart = { offset ->
                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                dragDropState.onDragStart(offset)
                          },
            onDragEnd = { dragDropState.onDragInterrupted() },
            onDragCancel = { dragDropState.onDragInterrupted() }
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun LazyGridItemScope.DraggableItem(
    dragDropState: LazyDraggableGridState,
    index: Int,
    modifier: Modifier = Modifier,
    content: @Composable (isDragging: Boolean) -> Unit,
) {
    val dragging = index == dragDropState.draggingItemIndex
    val draggingModifier = if (dragging) {
        Modifier
            .zIndex(1f)
            .graphicsLayer {
                translationX = dragDropState.draggingItemOffset.x
                translationY = dragDropState.draggingItemOffset.y
            }
    } else if (index == dragDropState.previousIndexOfDraggedItem) {
        Modifier
            .zIndex(1f)
            .graphicsLayer {
                translationX = dragDropState.previousItemOffset.value.x
                translationY = dragDropState.previousItemOffset.value.y
            }
    }  else {
        Modifier.animateItemPlacement()
    }
    Box(modifier = modifier.then(draggingModifier), propagateMinConstraints = true) {
        content(dragging)
    }
}