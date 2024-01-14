package com.serhiiromanchuk.utilitybills.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.domain.mocks.fakeUtilityService
import com.serhiiromanchuk.utilitybills.presentation.screen.home.ServiceItem
import com.serhiiromanchuk.utilitybills.presentation.viewmodel.ViewModelFactory
import com.serhiiromanchuk.utilitybills.ui.theme.UtilityBillsTheme
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
                Column {
                    ServiceItem(
                        modifier = Modifier.padding(16.dp),
                        utilityService = fakeUtilityService,
                        previousValueChange = {},
                        currentValueChange = {},
                        onEditServiceClick = { /*TODO*/ },
                        isEnabled = {}
                    )
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .background(Color.White)
                    )
                }
            }
        }
    }
}