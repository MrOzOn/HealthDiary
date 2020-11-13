package com.mrozon.core_api.navigation

import androidx.navigation.NavController

interface ListPersonNavigator {

    fun navigateToEditPerson(navController: NavController, title: String, id: Long)

    fun navigateToMeasureForPerson(navController: NavController, id: Long)
}