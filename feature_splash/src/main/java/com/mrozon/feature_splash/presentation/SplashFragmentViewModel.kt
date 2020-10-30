package com.mrozon.feature_splash.presentation

import com.mrozon.feature_splash.data.PreloadDataRepository
import com.mrozon.utils.base.BaseViewModel
import javax.inject.Inject

class SplashFragmentViewModel @Inject constructor(
    localUser: PreloadDataRepository
): BaseViewModel() {

    val currentUser = localUser.getLocalUser()

}