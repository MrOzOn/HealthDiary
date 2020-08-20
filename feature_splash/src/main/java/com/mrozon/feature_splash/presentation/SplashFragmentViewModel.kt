package com.mrozon.feature_splash.presentation

import com.mrozon.utils.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class SplashFragmentViewModel @Inject constructor(): BaseViewModel() {

    fun blaaa() {
        Timber.d("blaaa")
    }

    init {
        Timber.d("init SplashFragmentViewModel")
    }

}