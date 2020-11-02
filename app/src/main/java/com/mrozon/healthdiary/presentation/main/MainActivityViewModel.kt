package com.mrozon.healthdiary.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrozon.core_api.entity.User
import com.mrozon.core_api.providers.CoroutineContextProvider
import com.mrozon.healthdiary.data.UserRepository
import com.mrozon.utils.Event
import com.mrozon.utils.network.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val repository: UserRepository,
    private val coroutineContextProvider: CoroutineContextProvider
): ViewModel() {

    val currentUser = repository.getLocalUser()

    private val _cleared = MutableLiveData<Event<Unit>>()
    val cleared: LiveData<Event<Unit>>
        get() = _cleared

    fun logoutUser(user: User) {
        CoroutineScope(coroutineContextProvider.IO).launch(getJobErrorHandler()) {
            repository.clearLocalUser(user)
            withContext(coroutineContextProvider.Main){
                _cleared.value = Event(Unit)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Timber.e(e)
    }

}

