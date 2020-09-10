package com.mrozon.healthdiary.di.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mrozon.core_api.viewmodel.ViewModelKey
import com.mrozon.feature_splash.di.DaggerViewModelFactory
import com.mrozon.healthdiary.data.UserRepository
import com.mrozon.healthdiary.data.UserRepositoryImp
import com.mrozon.healthdiary.presentation.main.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainActivityModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    fun bindViewModel(viewmodel: MainActivityViewModel): ViewModel

    @Binds
    fun viewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    fun provideUserRepository(repository: UserRepositoryImp): UserRepository

}
