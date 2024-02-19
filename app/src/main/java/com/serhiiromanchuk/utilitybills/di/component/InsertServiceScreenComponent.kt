package com.serhiiromanchuk.utilitybills.di.component

import com.serhiiromanchuk.utilitybills.di.module.InsertServiceViewModelModule
import com.serhiiromanchuk.utilitybills.presentation.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Named

@Subcomponent(modules = [InsertServiceViewModelModule::class])
interface InsertServiceScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance @Named("utilityServiceId") utilityServiceId: Long,
            @BindsInstance @Named("billCreatorId") billCreatorId: Long,
        ) : InsertServiceScreenComponent
    }
}