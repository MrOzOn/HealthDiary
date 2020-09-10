package com.mrozon.feature_person.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mrozon.core_api.viewmodel.ViewModelKey
import com.mrozon.feature_person.data.PersonRepository
import com.mrozon.feature_person.data.PersonRepositoryImpl
import com.mrozon.feature_person.presentation.ListPersonFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ListPersonFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(ListPersonFragmentViewModel::class)
    fun bindViewModel(viewmodel: ListPersonFragmentViewModel): ViewModel

    @Binds
    fun viewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    fun providePersonRepository(repository: PersonRepositoryImpl): PersonRepository
}