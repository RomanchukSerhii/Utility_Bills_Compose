package com.serhiiromanchuk.utilitybills.presentation.core.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.serhiiromanchuk.utilitybills.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarApp(
    modifier: Modifier = Modifier,
    @StringRes titleId: Int? = null,
    actions: @Composable RowScope.() -> Unit = {},
    gradientColors: List<Color> = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.primaryContainer
    ),
    onBackPressed: (() -> Unit)? = null
) {

    TopAppBar(
        modifier = modifier.background(
            brush = Brush.verticalGradient(
                colors = gradientColors
            )
        ),
        title = {
            Text(
                modifier = Modifier.paddingFromBaseline(bottom = dimensionResource(id = R.dimen.padding_small)),
                text = if (titleId != null) stringResource(id = titleId) else "",
                maxLines = 1
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White.copy(alpha = 0f),
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            if (onBackPressed != null ) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = stringResource(R.string.back_to_previous_screen)
                    )
                }
            }
        },
        actions = actions
    )
}