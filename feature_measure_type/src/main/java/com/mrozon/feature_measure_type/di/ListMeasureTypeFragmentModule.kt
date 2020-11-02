package com.mrozon.feature_measure_type.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mrozon.core_api.viewmodel.ViewModelKey
import com.mrozon.feature_measure_type.data.MeasureTypeRepository
import com.mrozon.feature_measure_type.data.MeasureTypeRepositoryImpl
import com.mrozon.feature_measure_type.presentation.ListMeasureTypeFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ListMeasureTypeFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListMeasureTypeFragmentViewModel::class)
    fun bindViewModel(viewmodel: ListMeasureTypeFragmentViewModel): ViewModel

    @Binds
    fun viewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    fun provideMeasureTypeRepository(measureTypeRepositoryImpl: MeasureTypeRepositoryImpl): MeasureTypeRepository

}