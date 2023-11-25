package com.serhiiromanchuk.utilitybills.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.domain.model.MeasurementUnit
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import com.serhiiromanchuk.utilitybills.presentation.navigation.AppNavGraph
import com.serhiiromanchuk.utilitybills.presentation.navigation.Screen
import com.serhiiromanchuk.utilitybills.presentation.navigation.rememberNavigationState
import com.serhiiromanchuk.utilitybills.presentation.screen.main.MainScreenLayout
import com.serhiiromanchuk.utilitybills.presentation.screen.start.StartScreenLayout
import com.serhiiromanchuk.utilitybills.presentation.screen.start.StartScreenUiState
import com.serhiiromanchuk.utilitybills.presentation.screen.start.StartScreenUiState.*
import com.serhiiromanchuk.utilitybills.presentation.viewmodel.MainScreenViewModel
import com.serhiiromanchuk.utilitybills.presentation.viewmodel.StartScreenViewModel
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
                val navigationState = rememberNavigationState()

                AppNavGraph(
                    navHostController = navigationState.navHostController,
                    startScreenContent = {
                        val startScreenViewModel: StartScreenViewModel =
                            viewModel(factory = viewModelFactory)
                        val startScreenUiState = startScreenViewModel.screenUiState.collectAsState()
                        StartScreenLayout(
                            startScreenUiState = startScreenUiState.value,
                            onSaveButtonClick = { address, cardNumber ->
                                val isInsertSuccess =
                                    startScreenViewModel.insertBillItem(address, cardNumber)
                                if (isInsertSuccess) {
                                    navigationState.navigateTo(Screen.MainScreen.route)
                                }
                            },
                            skipStartScreen = {
                                navigationState.navigateTo(Screen.MainScreen.route)
                            }
                        )
                    },
                    mainScreenContent = {
                        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            LocalDate.now()
                        } else {
                            TODO("VERSION.SDK_INT < O")
                        }
                        val test = data.year
                        val utilityService = UtilityServiceItem(
                            name = "Газ",
                            address = "Грушевского 23, 235",
                            year = data.year,
                            month = data.month,
                            tariff = 7.98,
                            isMeterAvailable = true,
                            previousValue = 9624,
                            unitOfMeasurement = MeasurementUnit.CUBIC_METER
                        )
                        val mainScreenViewModel: MainScreenViewModel =
                            viewModel(factory = viewModelFactory)
                        MainScreenLayout(
                            utilityService = utilityService,
                            onDeleteButtonClick = { mainScreenViewModel.deleteBillItem() },
                            onEditClick = { /*TODO*/ },
                            onCheckIconClick = {},
                            previousValueChange = {},
                            currentValueChange = {}
                        )
                    },
                    insertServiceScreenContent = { /*TODO*/ },
                    billsArchiveScreenContent = { /*TODO*/ },
                    billScreenContent = { /*TODO*/ },
                    billDetailsScreenContent = { /*TODO*/ }
                )

            }
//            UtilityBillsTheme {
//                val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    LocalDate.now()
//                } else {
//                    TODO("VERSION.SDK_INT < O")
//                }
//                val test = data.year
//                BillLayout(
//                    utilityService = UtilityServiceItem(
//                        name = "Газ",
//                        address = "Грушевского 23, 235",
//                        year = data.year,
//                        month = data.month,
//                        tariff = 7.98,
//                        isMeterAvailable = true,
//                        previousValue = 9624,
//                        unitOfMeasurement = MeasurementUnit.CUBIC_METER
//                    ),
//                    onEditClick = { /*TODO*/ },
//                    onCheckIconClick = { /*TODO*/ },
//                    previousValueChange = {},
//                    currentValueChange = {}
//                )
//            }
        }
    }
}