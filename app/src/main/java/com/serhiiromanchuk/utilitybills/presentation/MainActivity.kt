package com.serhiiromanchuk.utilitybills.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.compose.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.domain.model.MeasurementUnit
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.presentation.screen.BillLayout
import com.serhiiromanchuk.utilitybills.presentation.viewmodel.ViewModelFactory
import java.time.LocalDate
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as UtilityBillsApplication).component
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            UtilityBillsTheme {
                val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDate.now()
                } else {
                    TODO("VERSION.SDK_INT < O")
                }
                val test = data.year
                BillLayout(
                    utilityService = UtilityServiceItem(
                        name = "Газ",
                        address = "Грушевского 23, 235",
                        year = data.year,
                        month = data.month,
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