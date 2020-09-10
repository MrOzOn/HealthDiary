package com.mrozon.healthdiary.di

import com.mrozon.core_api.navigation.*
import com.mrozon.healthdiary.navigation.*
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
    fun loginNavigator(navigator: LoginNavigatorImpl): LoginNavigator

    @Reusable
    @Binds
    fun registrationNavigator(navigator: RegistrationNavigatorImpl): RegistrationNavigator

    @Reusable
    @Binds
    fun listPersonNavigator(navigator: ListPersonNavigatorImpl): ListPersonNavigator

    @Reusable
    @Binds
    fun editPersonNavigator(navigator: EditPersonNavigatorImpl): EditPersonNavigator
}
