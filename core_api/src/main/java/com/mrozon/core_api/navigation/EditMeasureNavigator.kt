package com.mrozon.core_api.navigation

import androidx.navigation.NavController

interface EditMeasureNavigator {

    fun navigateToListMeasure(navController: NavController, id: Long, measureTypeId: Long)
}