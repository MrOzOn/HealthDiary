package com.mrozon.healthdiary.navigation

import androidx.navigation.NavController
import com.mrozon.core_api.navigation.LoginNavigator
import com.mrozon.core_api.navigation.SplashNavigator
import com.mrozon.healthdiary.R
import javax.inject.Inject

class LoginNavigatorImpl @Inject constructor()
    :LoginNavigator {

    override fun navigateToRegisterUser(navController: NavController) {
        navController.navigate(R.id.action_loginFragment_to_registrationFragment)
    }

    override fun navigateToListPerson(navController: NavController) {
        navController.navigate(R.id.action_loginFragment_to_listPersonFragment)
    }


}