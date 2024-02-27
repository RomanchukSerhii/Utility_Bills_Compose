package com.serhiiromanchuk.utilitybills.di.module

import androidx.lifecycle.ViewModel
import com.serhiiromanchuk.utilitybills.di.annotation.ViewModelKey
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_details.SharedBillViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface BillViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SharedBillViewModel::class)
    fun bindSharedBillViewModel(viewModel: SharedBillViewModel): ViewModel
}