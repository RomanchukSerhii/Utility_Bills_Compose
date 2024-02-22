package com.serhiiromanchuk.utilitybills.di.module

import androidx.lifecycle.ViewModel
import com.serhiiromanchuk.utilitybills.di.annotation.ViewModelKey
import com.serhiiromanchuk.utilitybills.presentation.screen.bill.BillViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface HomeScreenViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BillViewModel::class)
    fun bindHomeScreenViewModel(viewModel: BillViewModel): ViewModel
}