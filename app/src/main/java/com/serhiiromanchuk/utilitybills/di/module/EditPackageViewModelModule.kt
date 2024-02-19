package com.serhiiromanchuk.utilitybills.di.module

import androidx.lifecycle.ViewModel
import com.serhiiromanchuk.utilitybills.di.annotation.ViewModelKey
import com.serhiiromanchuk.utilitybills.presentation.screen.start.edit_package.EditPackageViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface EditPackageViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(EditPackageViewModel::class)
    fun bindEditPackageViewModel(viewModel: EditPackageViewModel): ViewModel
}