package com.mrozon.feature_measure.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mrozon.core_api.viewmodel.ViewModelKey
import com.mrozon.feature_measure.presentation.TabMeasureFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface TabMeasureFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(TabMeasureFragmentViewModel::class)
    fun bindViewModel(viewmodel: TabMeasureFragmentViewModel): ViewModel

    @Binds
    fun viewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}