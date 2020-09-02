package com.mrozon.feature_auth.presentation

import android.util.Patterns.EMAIL_ADDRESS
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.db.model.UserDb
import com.mrozon.core_api.mapper.UserToUserDbMapper
import com.mrozon.core_api.network.model.toUserDb
import com.mrozon.feature_auth.data.UserAuthRemoteDataSource
import com.mrozon.utils.base.BaseViewModel
//import com.mrozon.utils.extension.asFlow
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineLatest
import timber.log.Timber
import javax.inject.Inject

class LoginFragmentViewModel @Inject constructor(
    private val userAuthRemoteDataSource: UserAuthRemoteDataSource,
    private val healthDiaryDao: HealthDiaryDao
): BaseViewModel() {

    private val _progress = MutableLiveData<Boolean>(false)
    val progress: LiveData<Boolean>
        get() = _progress

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

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
        CoroutineScope(Dispatchers.IO).launch(getJobErrorHandler()) {
            val response = userAuthRemoteDataSource.loginUser(userName, userPsw)
                if (response.status == com.mrozon.utils.network.Result.Status.SUCCESS) {
                    val result = response.data
                    healthDiaryDao.insertUser(result?.toUserDb()!!)
                    Timber.d(result.token)
                    withContext(Dispatchers.Main) {
                        _progress.value = false
                    }
                } else if (response.status == com.mrozon.utils.network.Result.Status.ERROR) {
                    Timber.e(response.message!!)
                    withContext(Dispatchers.Main) {
                        _progress.value = false
                        _error.value = response.message!!
                    }
                }

        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Timber.e(e)
        _error.value = e.message
    }
}