package com.mrozon.core_api.navigation

interface NavigatorProvider {
    fun provideSplashNavigator(): SplashNavigator
    fun provideLoginNavigator(): LoginNavigator
    fun provideRegistrationNavigator(): RegistrationNavigator
}