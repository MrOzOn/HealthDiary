package com.mrozon.healthdiary.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.mrozon.core_api.navigation.RegistrationNavigator
import com.mrozon.healthdiary.R
import javax.inject.Inject

class RegistrationNavigatorImpl @Inject constructor()
    :RegistrationNavigator{

    override fun navigateToLoginUser(navController: NavController, userName: String) {
        val bundle = bundleOf("userName" to userName)
        navController.navigate(R.id.action_registrationFragment_to_loginFragment, bundle)
    }
}
