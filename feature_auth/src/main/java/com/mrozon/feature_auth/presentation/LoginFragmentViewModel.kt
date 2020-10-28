package com.mrozon.feature_auth.presentation

import android.util.Patterns.EMAIL_ADDRESS
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mrozon.core_api.entity.User
import com.mrozon.core_api.providers.CoroutineContextProvider
import com.mrozon.feature_auth.data.UserAuthRepository
import com.mrozon.feature_auth.data.UserAuthRepositoryImpl
import com.mrozon.utils.Event
import com.mrozon.utils.base.BaseViewModel
import com.mrozon.utils.network.Result
//import com.mrozon.utils.extension.asFlow
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class LoginFragmentViewModel @Inject constructor(
    private val repository: UserAuthRepository,
    private val coroutineContextProvider: CoroutineContextProvider
): BaseViewModel() {

    private val _loggedUser = MutableLiveData<Event<Result<User>>>()
    val loggedUser: LiveData<Event<Result<User>>>
        get() = _loggedUser

    @ExperimentalCoroutinesApi
    val userNameChannel = ConflatedBroadcastChannel<String>()

    @ExperimentalCoroutinesApi
    val userPasswordChannel = ConflatedBroadcastChannel<String>()

    @FlowPreview
    @ExperimentalCoroutinesApi
    val enableLogin = object: MutableLiveData<Boolean>() {

        override fun onActive() {
            value?.let { return }
            viewModelScope.launch {
                var job: Deferred<Unit>? = null
                userNameChannel.asFlow()
                    .combine(userPasswordChannel.asFlow()) { name, psw ->
                        Pair(name, psw)
                    }
                    .collect {
                        job?.cancel()
                        job = async(coroutineContextProvider.Main) {
                            value = validateInputData(it)
                        }
                    }
            }
        }
    }

    private fun validateInputData(pair: Pair<String,String>) =
        (EMAIL_ADDRESS.matcher(pair.first).matches()) and (pair.second.isNotEmpty())


    @ExperimentalCoroutinesApi
    fun loginUser(){
        val userName = userNameChannel.value
        val psw = userPasswordChannel.value
        viewModelScope.launch(coroutineContextProvider.IO){
            repository.loginUser(userName,psw).collect {
                withContext(coroutineContextProvider.Main) {
                    _loggedUser.value = Event(it)
                }
            }
        }

    }
}