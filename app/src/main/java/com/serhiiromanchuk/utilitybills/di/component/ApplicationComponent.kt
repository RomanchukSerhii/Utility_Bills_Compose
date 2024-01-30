package com.serhiiromanchuk.utilitybills.di.component

import android.app.Application
import com.serhiiromanchuk.utilitybills.di.annotation.ApplicationScope
import com.serhiiromanchuk.utilitybills.di.module.DataModule
import com.serhiiromanchuk.utilitybills.di.module.ViewModelModule
import com.serhiiromanchuk.utilitybills.presentation.ViewModelFactory
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory

    fun getInsertServiceScreenComponentFactory(): InsertServiceScreenComponent.Factory

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}