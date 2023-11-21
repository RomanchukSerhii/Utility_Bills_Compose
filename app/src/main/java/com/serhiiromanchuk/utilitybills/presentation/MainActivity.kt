package com.serhiiromanchuk.utilitybills.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.compose.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.domain.model.MeasurementUnit
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UtilityBillsTheme {
                BillLayout(
                    utilityService = UtilityServiceItem(
                        name = "Газ",
                        address = "Грушевского 23, 235",
                        tariff = 7.98,
                        isMeterAvailable = true,
                        previousValue = 9624,
                        unitOfMeasurement = MeasurementUnit.CUBIC_METER
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