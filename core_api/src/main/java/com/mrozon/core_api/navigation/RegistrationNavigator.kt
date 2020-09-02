package com.mrozon.core_api.navigation

import androidx.navigation.NavController

interface RegistrationNavigator {
    fun navigateToLoginUser(navController: NavController, userName: String="")
}