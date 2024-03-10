package com.serhiiromanchuk.utilitybills.di.module

import androidx.lifecycle.ViewModel
import com.serhiiromanchuk.utilitybills.di.annotation.ViewModelKey
import com.serhiiromanchuk.utilitybills.presentation.screen.main.MainScreenViewModel
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package.AddPackageViewModel
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageViewModel
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
    @ViewModelKey(ChoosePackageViewModel::class)
    fun bindStartScreenViewModel(viewModel: ChoosePackageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddPackageViewModel::class)
    fun bindAddBillViewModel(viewModel: AddPackageViewModel): ViewModel
}