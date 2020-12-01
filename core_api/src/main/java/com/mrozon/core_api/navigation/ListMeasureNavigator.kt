package com.mrozon.core_api.navigation

import androidx.navigation.NavController

interface ListMeasureNavigator {

    fun navigateToEditMeasure(navController: NavController, title: String, id: Long)

}