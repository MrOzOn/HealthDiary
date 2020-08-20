package com.mrozon.feature_splash.presentation.di

import androidx.lifecycle.ViewModel
import com.mrozon.core_api.viewmodel.ViewModelKey
import com.mrozon.feature_splash.presentation.SplashFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SplashFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(SplashFragmentViewModel::class)
    abstract fun bindViewModel(viewmodel: SplashFragmentViewModel): ViewModel
}