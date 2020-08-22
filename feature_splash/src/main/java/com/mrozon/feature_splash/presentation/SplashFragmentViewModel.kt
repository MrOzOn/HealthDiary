package com.mrozon.feature_splash.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mrozon.feature_splash.repository.LocalUser
import com.mrozon.utils.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class SplashFragmentViewModel @Inject constructor(
    private val localUser: LocalUser
): BaseViewModel() {

    val currentUser = localUser.getLocalUser()

}