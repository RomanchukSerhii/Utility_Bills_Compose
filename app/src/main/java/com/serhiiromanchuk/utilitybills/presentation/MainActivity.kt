package com.serhiiromanchuk.utilitybills.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.serhiiromanchuk.utilitybills.presentation.navigation.RootNavigationGraph
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            enableEdgeToEdge(
                navigationBarStyle = SystemBarStyle.auto(
                    MaterialTheme.colorScheme.background.toArgb(),
                    MaterialTheme.colorScheme.background.toArgb()
                )
            )
            UtilityBillsTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RootNavigationGraph()
                }
            }
        }
    }
}