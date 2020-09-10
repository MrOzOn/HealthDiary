package com.mrozon.feature_auth.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.mapper.UserToUserDbMapper
import com.mrozon.core_api.viewmodel.ViewModelKey
import com.mrozon.feature_auth.data.UserAuthRepository
import com.mrozon.feature_auth.data.UserAuthRepositoryImpl
import com.mrozon.feature_auth.presentation.LoginFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface LoginFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginFragmentViewModel::class)
    fun bindViewModel(viewmodel: LoginFragmentViewModel): ViewModel

    @Binds
    fun viewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    fun provideUserAuthRepository(repository: UserAuthRepositoryImpl): UserAuthRepository

}