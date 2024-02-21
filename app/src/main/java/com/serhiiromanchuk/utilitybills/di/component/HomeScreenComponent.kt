package com.serhiiromanchuk.utilitybills.di.component

import com.serhiiromanchuk.utilitybills.di.module.HomeScreenViewModelModule
import com.serhiiromanchuk.utilitybills.presentation.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [HomeScreenViewModelModule::class])
interface HomeScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance billId: Long) : HomeScreenComponent
    }
}