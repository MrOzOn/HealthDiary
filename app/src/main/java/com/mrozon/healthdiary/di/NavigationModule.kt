package com.mrozon.healthdiary.di

import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.mapper.UserToUserDbMapper
import com.mrozon.core_api.navigation.LoginNavigator
import com.mrozon.core_api.navigation.RegistrationNavigator
import com.mrozon.core_api.navigation.SplashNavigator
import com.mrozon.feature_splash.repository.LocalUser
import com.mrozon.feature_splash.repository.LocalUserImp
import com.mrozon.healthdiary.navigation.LoginNavigatorImpl
import com.mrozon.healthdiary.navigation.RegistrationNavigatorImpl
import com.mrozon.healthdiary.navigation.SplashNavigatorImpl
import com.mrozon.utils.base.BaseFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
interface NavigationModule {

    @Reusable
    @Binds
    fun splashNavigator(navigator: SplashNavigatorImpl): SplashNavigator

    @Reusable
    @Binds
    fun loginNavigator(navigator: LoginNavigatorImpl): LoginNavigator

    @Reusable
    @Binds
    fun registrationNavigator(navigator: RegistrationNavigatorImpl): RegistrationNavigator
}