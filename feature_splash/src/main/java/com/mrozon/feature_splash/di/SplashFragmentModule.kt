package com.mrozon.feature_splash.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.mapper.UserToUserDbMapper
import com.mrozon.core_api.viewmodel.ViewModelKey
import com.mrozon.feature_splash.presentation.SplashFragmentViewModel
import com.mrozon.feature_splash.data.PreloadDataRepository
import com.mrozon.feature_splash.data.PreloadDataRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface SplashFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(SplashFragmentViewModel::class)
    fun bindViewModel(viewmodel: SplashFragmentViewModel): ViewModel

    @Binds
    fun viewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    fun providePreloadDataRepository(repository: PreloadDataRepositoryImp): PreloadDataRepository

}