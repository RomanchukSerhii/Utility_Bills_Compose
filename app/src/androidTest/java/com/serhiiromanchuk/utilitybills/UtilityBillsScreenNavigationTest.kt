package com.serhiiromanchuk.utilitybills

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.serhiiromanchuk.utilitybills.presentation.navigation.AppNavGraph
import com.serhiiromanchuk.utilitybills.presentation.navigation.Screen
import com.serhiiromanchuk.utilitybills.presentation.navigation.rememberNavigationState
import com.serhiiromanchuk.utilitybills.presentation.screen.main.MainScreenLayout
import com.serhiiromanchuk.utilitybills.presentation.screen.main.MainScreenUiState
import com.serhiiromanchuk.utilitybills.presentation.screen.start.StartScreenLayout
import com.serhiiromanchuk.utilitybills.presentation.screen.start.StartScreenUiState
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UtilityBillsScreenNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController


    @Before
    fun setupUtilityBillsNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            val navigationState = rememberNavigationState(navController)
            AppNavGraph(
                navHostController = navigationState.navHostController,
                startScreenContent = {
                    StartScreenLayout(
                        startScreenUiState = StartScreenUiState.Initial,
                        onSaveButtonClick = { _, _ -> },
                        skipStartScreen = {}
                    )
                },
                mainScreenContent = {
                    MainScreenLayout(
                        mainScreenUiState = MainScreenUiState.Initial,
                        onDeleteButtonClick = { /*TODO*/ },
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
    }

    @Test
    fun utilityBillsNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.StartScreen.route)
    }
}