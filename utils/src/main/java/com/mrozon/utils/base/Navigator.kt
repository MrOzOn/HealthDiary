package com.mrozon.utils.base

import androidx.navigation.NavController

interface Navigator {
    var navController: NavController?

    fun bind(navController: NavController) {
        this.navController = navController
    }

    fun unbind() {
        this.navController = null
    }
}