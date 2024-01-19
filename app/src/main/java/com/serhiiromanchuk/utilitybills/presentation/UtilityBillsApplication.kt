package com.serhiiromanchuk.utilitybills.presentation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.serhiiromanchuk.utilitybills.di.component.ApplicationComponent
import com.serhiiromanchuk.utilitybills.di.component.DaggerApplicationComponent

class UtilityBillsApplication : Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as UtilityBillsApplication).component
}