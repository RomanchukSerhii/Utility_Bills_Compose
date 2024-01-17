package com.serhiiromanchuk.utilitybills.presentation.core.components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.presentation.core.annotations.DarkLightPreviews
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme

@Composable
fun ErrorPointer(
    modifier: Modifier = Modifier,
    color: Color
) {
    Canvas(modifier = modifier
        .fillMaxWidth()
        .height(7.dp)
        .background(MaterialTheme.colorScheme.surfaceVariant)) {
        drawPath(
            path = Path().apply {
                moveTo(size.width - 3.dp.toPx(), 6.dp.toPx())
                lineTo(size.width - 8.dp.toPx(), 0.dp.toPx())
                lineTo(size.width - 13.dp.toPx(), 6.dp.toPx())
                lineTo(size.width - 3.dp.toPx(), 6.dp.toPx())
            },
            color = color
        )
        drawLine(
            color = color,
            start = Offset(0.dp.toPx(), 6.dp.toPx()),
            end = Offset(size.width, 6.dp.toPx()),
            strokeWidth = 3.dp.toPx()
        )
    }
}

@DarkLightPreviews
@Composable
fun ErrorPointerPreview() {
    UtilityBillsTheme {
        ErrorPointer(
            color = MaterialTheme.colorScheme.error
        )
    }
}