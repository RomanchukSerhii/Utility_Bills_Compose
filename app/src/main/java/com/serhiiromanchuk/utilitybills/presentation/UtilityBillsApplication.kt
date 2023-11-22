package com.serhiiromanchuk.utilitybills.presentation

import android.app.Application
import com.serhiiromanchuk.utilitybills.di.component.ApplicationComponent
import com.serhiiromanchuk.utilitybills.di.component.DaggerApplicationComponent

class UtilityBillsApplication : Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}