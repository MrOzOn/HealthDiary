package com.mrozon.feature_person.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mrozon.core_api.viewmodel.ViewModelKey
import com.mrozon.feature_person.presentation.EditPersonFragmentViewModel
import com.mrozon.feature_person.presentation.ListPersonFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface EditPersonFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(EditPersonFragmentViewModel::class)
    fun bindViewModel(viewmodel: EditPersonFragmentViewModel): ViewModel

    @Binds
    fun viewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}