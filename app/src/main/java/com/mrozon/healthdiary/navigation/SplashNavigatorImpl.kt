package com.mrozon.healthdiary.navigation

import androidx.navigation.NavController
import com.mrozon.core_api.navigation.SplashNavigator
import com.mrozon.healthdiary.R
import javax.inject.Inject

class SplashNavigatorImpl @Inject constructor()
    :SplashNavigator {

    override fun navigateToAuth(navController: NavController) {
        navController.navigate(R.id.action_splashFragment_to_loginFragment)
    }

    override fun navigateToListPerson(navController: NavController) {
        navController.navigate(R.id.action_splashFragment_to_listPersonFragment)
    }

}