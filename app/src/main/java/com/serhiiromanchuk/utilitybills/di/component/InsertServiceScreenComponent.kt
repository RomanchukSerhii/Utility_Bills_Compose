package com.serhiiromanchuk.utilitybills.di.component

import com.serhiiromanchuk.utilitybills.di.module.InsertServiceViewModelModule
import com.serhiiromanchuk.utilitybills.presentation.viewmodel.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        InsertServiceViewModelModule::class
    ]
)
interface InsertServiceScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance utilityServiceId: Long,
            @BindsInstance address: String,
        ) : InsertServiceScreenComponent
    }
}