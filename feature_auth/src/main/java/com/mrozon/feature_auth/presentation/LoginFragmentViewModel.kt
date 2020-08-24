package com.mrozon.feature_auth.presentation

import android.util.Patterns.EMAIL_ADDRESS
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mrozon.utils.base.BaseViewModel
//import com.mrozon.utils.extension.asFlow
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineLatest
import javax.inject.Inject

class LoginFragmentViewModel @Inject constructor(
): BaseViewModel() {

    private val _progress = MutableLiveData<Boolean>(false)
    val progress: LiveData<Boolean>
        get() = _progress

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
                        job = async(Dispatchers.Main) {
                            value = validateInputData(it)
                        }
                    }
            }
        }
    }

    private fun validateInputData(pair: Pair<String,String>) =
        (EMAIL_ADDRESS.matcher(pair.first).matches()) and (pair.second.isNotEmpty())

    fun loginUser(userName: String, userPsw: String) {
        _progress.value = true
    }
}