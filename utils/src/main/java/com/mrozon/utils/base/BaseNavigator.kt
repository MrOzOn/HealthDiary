package com.mrozon.utils.base

import androidx.navigation.NavController

abstract class BaseNavigator : Navigator {

    override var navController: NavController? = null

}