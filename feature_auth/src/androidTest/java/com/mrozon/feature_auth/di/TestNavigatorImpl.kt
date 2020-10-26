package com.mrozon.feature_auth.di

import androidx.navigation.NavController
import com.mrozon.core_api.navigation.EditPersonNavigator
import com.mrozon.core_api.navigation.ListPersonNavigator
import com.mrozon.core_api.navigation.LoginNavigator
import com.mrozon.core_api.navigation.RegistrationNavigator
import javax.inject.Inject

class TestNavigatorImpl @Inject constructor(): RegistrationNavigator, LoginNavigator,
    ListPersonNavigator, EditPersonNavigator {

    override fun navigateToLoginUser(navController: NavController, userName: String) {

    }

    override fun navigateToRegisterUser(navController: NavController) {
    }

    override fun navigateToListPerson(navController: NavController) {
    }

    override fun navigateToEditPerson(navController: NavController, title: String, id: Long) {

    }
}