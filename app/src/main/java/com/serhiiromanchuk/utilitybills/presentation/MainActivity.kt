package com.serhiiromanchuk.utilitybills.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.compose.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UtilityBillsTheme {
                UtilityServiceLayout(
                    utilityService = UtilityServiceItem(
                        name = "Газ",
                        tariff = 7.98,
                        isMeterAvailable = true,
                        previousValue = 9624,
                    ),
                    onEditClick = { /*TODO*/ },
                    onCheckIconClick = { /*TODO*/ },
                    previousValueChange = {},
                    currentValueChange = {}
                )
            }
        }
    }
}