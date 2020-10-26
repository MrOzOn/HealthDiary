package com.mrozon.feature_auth.di

import com.mrozon.core_api.navigation.*
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface NavigationModule {

    @Reusable
    @Binds
    fun splashNavigator(navigator: SplashNavigatorImpl): SplashNavigator

    @Reusable
    @Binds
    fun loginNavigator(navigator: TestNavigatorImpl): LoginNavigator

    @Reusable
    @Binds
    fun registrationNavigator(navigator: TestNavigatorImpl): RegistrationNavigator

    @Reusable
    @Binds
    fun listPersonNavigator(navigator: TestNavigatorImpl): ListPersonNavigator

    @Reusable
    @Binds
    fun editPersonNavigator(navigator: TestNavigatorImpl): EditPersonNavigator
}
