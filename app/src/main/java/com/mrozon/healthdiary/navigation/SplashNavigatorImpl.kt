package com.mrozon.healthdiary.navigation

import com.mrozon.core_api.navigation.SplashNavigator
import com.mrozon.healthdiary.R
import com.mrozon.utils.base.BaseNavigator
import javax.inject.Inject

class SplashNavigatorImpl @Inject constructor()
    : SplashNavigator, BaseNavigator() {

//    override fun navigateToGameVideo() {
//        navController?.navigate(R.id.action_gameListFragment_to_gameVideoFragment)
//            ?: throw IllegalStateException("NavController must be bound to ${this.javaClass.name} before processing navigation")
//    }

}