package com.mrozon.feature_splash.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mrozon.core_api.entity.Person
import com.mrozon.core_api.entity.User
import com.mrozon.core_api.providers.CoroutineContextProvider
import com.mrozon.feature_splash.data.PreloadDataRepository
import com.mrozon.utils.Event
import com.mrozon.utils.base.BaseViewModel
import com.mrozon.utils.network.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class SplashFragmentViewModel @Inject constructor(
    repository: PreloadDataRepository,
    private val coroutineContextProvider: CoroutineContextProvider
): BaseViewModel() {

    private val _currentUser = MutableLiveData<Event<Result<User?>>>()
    val currentUser: LiveData<Event<Result<User?>>>
        get() = _currentUser

    init {
        viewModelScope.launch(coroutineContextProvider.IO){
            repository.getPreloadData().collect {
                withContext(coroutineContextProvider.Main) {
                    _currentUser.value = Event(it)
                }
            }
        }
    }

}