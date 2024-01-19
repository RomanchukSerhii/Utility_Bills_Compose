package com.serhiiromanchuk.utilitybills

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.serhiiromanchuk.utilitybills.presentation.navigation.AppNavGraph
import com.serhiiromanchuk.utilitybills.presentation.navigation.Screen
import com.serhiiromanchuk.utilitybills.presentation.navigation.rememberNavigationState
import com.serhiiromanchuk.utilitybills.presentation.screen.main.MainScreen
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
                    var screenUiState by remember { mutableStateOf<StartScreenUiState>(StartScreenUiState.Initial) }
                    StartScreenLayout(
                        startScreenUiState = screenUiState,
                        navigationState = navigationState,
                        onEvent = {
                            screenUiState = StartScreenUiState.LoadingMainScreen
                        }
                    )
                },
                homeScreenContent = {
                    MainScreen()
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

    @Test
    fun utilityBillsNavHost_clickSaveOnStartScreen_navigatesToMainScreen() {
        navigateToMainScreen()
        navController.assertCurrentRouteName(Screen.HomeScreen.route)
    }

    private fun navigateToMainScreen() {
        composeTestRule.onNodeWithStringId(R.string.save).performClick()
    }
}