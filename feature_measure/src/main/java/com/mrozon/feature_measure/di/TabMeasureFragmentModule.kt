package com.mrozon.feature_measure.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mrozon.core_api.viewmodel.ViewModelKey
import com.mrozon.feature_measure.data.MeasureRepository
import com.mrozon.feature_measure.data.MeasureRepositoryImpl
import com.mrozon.feature_measure.presentation.EditMeasureFragmentViewModel
import com.mrozon.feature_measure.presentation.ListMeasureFragmentViewModel
import com.mrozon.feature_measure.presentation.TabMeasureFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface TabMeasureFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(TabMeasureFragmentViewModel::class)
    fun bindTabMeasureFragmentViewModel(viewmodel: TabMeasureFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListMeasureFragmentViewModel::class)
    fun bindListMeasureFragmentViewModel(viewmodel: ListMeasureFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditMeasureFragmentViewModel::class)
    fun bindEditMeasureFragmentViewModel(viewmodel: EditMeasureFragmentViewModel): ViewModel

    @Binds
    fun viewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    fun providePersonRepository(repository: MeasureRepositoryImpl): MeasureRepository
}