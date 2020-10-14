package com.mrozon.feature_auth.di

import androidx.navigation.NavController
import com.mrozon.core_api.navigation.SplashNavigator
import javax.inject.Inject

class SplashNavigatorImpl @Inject constructor()
    :SplashNavigator {

    override fun navigateToAuth(navController: NavController) {
    }

    override fun navigateToListPerson(navController: NavController) {
    }

}
