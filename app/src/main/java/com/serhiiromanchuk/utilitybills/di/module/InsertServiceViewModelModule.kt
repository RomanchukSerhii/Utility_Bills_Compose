package com.serhiiromanchuk.utilitybills.di.module

import androidx.lifecycle.ViewModel
import com.serhiiromanchuk.utilitybills.di.annotation.ViewModelKey
import com.serhiiromanchuk.utilitybills.presentation.screen.insert_service.InsertUtilityServiceViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface InsertServiceViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(InsertUtilityServiceViewModel::class)
    fun bindInsertServiceViewModel(viewModel: InsertUtilityServiceViewModel): ViewModel
}