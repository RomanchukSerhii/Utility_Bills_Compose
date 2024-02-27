package com.serhiiromanchuk.utilitybills.di.component

import com.serhiiromanchuk.utilitybills.di.module.BillViewModelModule
import com.serhiiromanchuk.utilitybills.presentation.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [BillViewModelModule::class])
interface BillHomeScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance billId: Long,
            @BindsInstance month: String
        ): BillHomeScreenComponent
    }
}