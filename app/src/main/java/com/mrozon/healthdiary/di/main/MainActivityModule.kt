package com.mrozon.healthdiary.di.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.mapper.UserToUserDbMapper
import com.mrozon.core_api.viewmodel.ViewModelKey
import com.mrozon.feature_splash.di.DaggerViewModelFactory
import com.mrozon.healthdiary.presentation.main.MainActivityViewModel
import com.mrozon.healthdiary.repository.LocalUserDataSource
import com.mrozon.healthdiary.repository.LocalUserDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class MainActivityModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindViewModel(viewmodel: MainActivityViewModel): ViewModel

    @Binds
    abstract fun viewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Module
    companion object {

        @Provides
        @Singleton
        @JvmStatic
        fun provideLocalUser(
            healthDiaryDao: HealthDiaryDao,
            mapper: UserToUserDbMapper
        ): LocalUserDataSource = LocalUserDataSourceImp(healthDiaryDao, mapper)
    }

}