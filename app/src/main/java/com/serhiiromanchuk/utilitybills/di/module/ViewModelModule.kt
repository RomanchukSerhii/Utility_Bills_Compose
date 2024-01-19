package com.serhiiromanchuk.utilitybills.di.module

import androidx.lifecycle.ViewModel
import com.serhiiromanchuk.utilitybills.di.annotation.ViewModelKey
import com.serhiiromanchuk.utilitybills.presentation.screen.home.HomeScreenViewModel
import com.serhiiromanchuk.utilitybills.presentation.screen.insertservice.InsertUtilityServiceViewModel
import com.serhiiromanchuk.utilitybills.presentation.viewmodel.MainScreenViewModel
import com.serhiiromanchuk.utilitybills.presentation.viewmodel.StartScreenViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(StartScreenViewModel::class)
    fun bindStartScreenViewModel(viewModel: StartScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeScreenViewModel::class)
    fun bindHomeScreenViewModel(viewModel: HomeScreenViewModel): ViewModel
}