package com.serhiiromanchuk.utilitybills.di.module

import androidx.lifecycle.ViewModel
import com.serhiiromanchuk.utilitybills.di.annotation.ViewModelKey
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_generation.BillGenerationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface BillGenerationViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BillGenerationViewModel::class)
    fun bindHomeScreenViewModel(viewModel: BillGenerationViewModel): ViewModel
}