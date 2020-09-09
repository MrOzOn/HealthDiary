package com.mrozon.healthdiary.navigation

import androidx.navigation.NavController
import com.mrozon.core_api.navigation.EditPersonNavigator
import com.mrozon.healthdiary.R
import javax.inject.Inject

class EditPersonNavigatorImpl @Inject constructor()
    : EditPersonNavigator {

    override fun navigateToListPerson(navController: NavController) {
        navController.navigate(R.id.action_editPersonFragment_to_listPersonFragment)
    }
}