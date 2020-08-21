package com.mrozon.healthdiary.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mrozon.healthdiary.repository.LocalUser
import com.mrozon.utils.base.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val localUser: LocalUser
): ViewModel() {

    val currentUser = localUser.getLocalUser()

}
