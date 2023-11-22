package com.serhiiromanchuk.utilitybills.di.module

import android.app.Application
import com.serhiiromanchuk.utilitybills.data.AppDataBase
import com.serhiiromanchuk.utilitybills.data.dao.BillDao
import com.serhiiromanchuk.utilitybills.data.dao.UtilityServiceDao
import com.serhiiromanchuk.utilitybills.data.repositoryimpl.BillRepositoryImpl
import com.serhiiromanchuk.utilitybills.data.repositoryimpl.UtilityServiceRepositoryImpl
import com.serhiiromanchuk.utilitybills.di.annotation.ApplicationScope
import com.serhiiromanchuk.utilitybills.domain.repository.BillRepository
import com.serhiiromanchuk.utilitybills.domain.repository.UtilityServiceRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindBillRepository(billRepositoryImpl: BillRepositoryImpl): BillRepository

    @ApplicationScope
    @Binds
    fun bindUtilityServiceRepository(utilityServiceRepositoryImpl: UtilityServiceRepositoryImpl): UtilityServiceRepository

    companion object {

        @ApplicationScope
        @Provides
        fun providesBillDao(application: Application): BillDao {
            return AppDataBase.getInstance(application).billDao()
        }

        @ApplicationScope
        @Provides
        fun providesUtilityServiceDao(application: Application): UtilityServiceDao {
            return AppDataBase.getInstance(application).utilityServiceDao()
        }
    }
}