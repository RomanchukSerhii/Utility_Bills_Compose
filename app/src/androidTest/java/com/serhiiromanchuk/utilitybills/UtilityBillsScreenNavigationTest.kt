package com.serhiiromanchuk.utilitybills

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.serhiiromanchuk.utilitybills.presentation.navigation.Screen
import com.serhiiromanchuk.utilitybills.presentation.navigation.rememberNavigationState
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