package com.mrozon.feature_auth.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mrozon.core_api.viewmodel.ViewModelKey
import com.mrozon.feature_auth.data.UserAuthRepository
import com.mrozon.feature_auth.data.UserAuthRepositoryImpl
import com.mrozon.feature_auth.presentation.LoginFragmentViewModel
import com.mrozon.feature_auth.presentation.RegistrationFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface RegistrationFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationFragmentViewModel::class)
    fun bindViewModel(viewmodel: RegistrationFragmentViewModel): ViewModel

    @Binds
    fun viewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    fun provideUserAuthRepository(repository: UserAuthRepositoryImpl): UserAuthRepository

}