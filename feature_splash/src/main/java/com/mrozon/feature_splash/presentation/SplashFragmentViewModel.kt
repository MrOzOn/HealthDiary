package com.mrozon.feature_splash.presentation

import com.mrozon.feature_splash.data.LocalUserRepository
import com.mrozon.utils.base.BaseViewModel
import javax.inject.Inject

class SplashFragmentViewModel @Inject constructor(
    localUser: LocalUserRepository
): BaseViewModel() {

    val currentUser = localUser.getLocalUser()

}