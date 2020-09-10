package com.mrozon.healthdiary.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mrozon.core_api.entity.User
import com.mrozon.healthdiary.data.UserRepository
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    val currentUser = userRepository.getLocalUser()

    private val _cleared = MutableLiveData<Boolean>(false)
    val cleared: LiveData<Boolean>
        get() = _cleared

    fun logoutUser(user: User) {
        CoroutineScope(Dispatchers.IO).launch(getJobErrorHandler()) {
            userRepository.clearLocalUser(user)
            withContext(Dispatchers.Main){
                _cleared.value = true
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Timber.e(e)
    }

}
