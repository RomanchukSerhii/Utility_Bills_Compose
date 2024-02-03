package com.serhiiromanchuk.utilitybills.di.module

import androidx.lifecycle.ViewModel
import com.serhiiromanchuk.utilitybills.di.annotation.ViewModelKey
import com.serhiiromanchuk.utilitybills.presentation.screen.add_bill.AddBillViewModel
import com.serhiiromanchuk.utilitybills.presentation.screen.home.HomeScreenViewModel
import com.serhiiromanchuk.utilitybills.presentation.screen.main.MainScreenViewModel
import com.serhiiromanchuk.utilitybills.presentation.screen.choose_bill.ChooseBillScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainScreenViewModel::class)
    fun bindMainScreenViewModel(viewModel: MainScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChooseBillScreenViewModel::class)
    fun bindStartScreenViewModel(viewModel: ChooseBillScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeScreenViewModel::class)
    fun bindHomeScreenViewModel(viewModel: HomeScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddBillViewModel::class)
    fun bindAddBillViewModel(viewModel: AddBillViewModel): ViewModel
}