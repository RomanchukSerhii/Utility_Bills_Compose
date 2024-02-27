package com.serhiiromanchuk.utilitybills.di.component

import com.serhiiromanchuk.utilitybills.di.module.BillGenerationViewModelModule
import com.serhiiromanchuk.utilitybills.presentation.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [BillGenerationViewModelModule::class])
interface BillGenerationScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance billId: Long): BillGenerationScreenComponent
    }
}