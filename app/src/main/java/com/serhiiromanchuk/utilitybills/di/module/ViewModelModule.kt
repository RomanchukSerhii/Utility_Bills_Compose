package com.serhiiromanchuk.utilitybills.di.module

import androidx.lifecycle.ViewModel
import com.serhiiromanchuk.utilitybills.di.annotation.ApplicationScope
import com.serhiiromanchuk.utilitybills.di.annotation.ViewModelKey
import com.serhiiromanchuk.utilitybills.presentation.viewmodel.InsertUtilityServiceViewModel
import com.serhiiromanchuk.utilitybills.presentation.viewmodel.MainScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(InsertUtilityServiceViewModel::class)
    fun bindInsertUtilityServiceViewModel(viewModel: InsertUtilityServiceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainScreenViewModel::class)
    fun bindMainScreenViewModel(viewModel: MainScreenViewModel): ViewModel
}