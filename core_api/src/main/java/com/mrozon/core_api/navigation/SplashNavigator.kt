package com.mrozon.core_api.navigation

import androidx.navigation.NavController


interface SplashNavigator {
    fun navigateToAuth(navController: NavController)
    fun navigateToListPerson(navController: NavController)
}