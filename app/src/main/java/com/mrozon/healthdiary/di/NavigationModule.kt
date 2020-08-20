package com.mrozon.healthdiary.di

import com.mrozon.core_api.navigation.SplashNavigator
import com.mrozon.healthdiary.navigation.SplashNavigatorImpl
import dagger.Binds
import dagger.Module

@Module
interface NavigationModule {

    @Binds
    fun splashNavigator(navigator: SplashNavigatorImpl): SplashNavigator

}