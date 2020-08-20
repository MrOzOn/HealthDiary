package com.mrozon.feature_splash.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mrozon.core_api.viewmodel.ViewModelKey
import com.mrozon.feature_splash.presentation.SplashFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SplashFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(SplashFragmentViewModel::class)
    fun bindViewModel(viewmodel: SplashFragmentViewModel): ViewModel

    @Binds
    fun viewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}