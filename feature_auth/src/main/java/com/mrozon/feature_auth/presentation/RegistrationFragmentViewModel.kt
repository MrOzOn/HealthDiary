package com.mrozon.feature_auth.presentation

import android.util.EventLog
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
import kotlin.coroutines.CoroutineContext

class RegistrationFragmentViewModel @Inject constructor(
    private val repository: UserAuthRepository,
    private val coroutineContextProvider: CoroutineContextProvider
): BaseViewModel() {

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>>
        get() = _error

    private val _registeredUser = MutableLiveData<Event<Result<User>>>()
    val registeredUser: LiveData<Event<Result<User>>>
        get() = _registeredUser

    @ExperimentalCoroutinesApi
    val emailChannel = ConflatedBroadcastChannel<String>()

    @ExperimentalCoroutinesApi
    val passwordChannel = ConflatedBroadcastChannel<String>()

    @ExperimentalCoroutinesApi
    val passwordConfirmChannel = ConflatedBroadcastChannel<String>()

    @ExperimentalCoroutinesApi
    val firstNameChannel = ConflatedBroadcastChannel<String>()

    @ExperimentalCoroutinesApi
    val lastNameChannel = ConflatedBroadcastChannel<String>()

    @FlowPreview
    @ExperimentalCoroutinesApi
    val validateData = object: MutableLiveData<ValidateDataError>() {

        override fun onActive() {
            value?.let { return }
            viewModelScope.launch {
                var job: Deferred<Unit>? = null
                combine(
                    emailChannel.asFlow(),
                    passwordChannel.asFlow(),
                    passwordConfirmChannel.asFlow(),
                    firstNameChannel.asFlow(),
                    lastNameChannel.asFlow()
                ) { email: String, psw1: String, psw2: String, firstName: String, lastName: String ->
                    arrayListOf(email, psw1, psw2, firstName, lastName)
                }
                    .collect {
                        job?.cancel()
                        job = async(coroutineContextProvider.Main) {
                            value = if (!EMAIL_ADDRESS.matcher(it[0]).matches())
                                ValidateDataError.INCORRECT_EMAIL
                            else if (it[1].isEmpty())
                                ValidateDataError.PASSWORD_EMPTY
                            else if (it[2].isEmpty())
                                ValidateDataError.PASSWORD_AGAIN_EMPTY
                            else if (it[1] != it[2])
                                ValidateDataError.PASSWORD_NOT_EQUAL
                            else if (it[3].isEmpty())
                                ValidateDataError.FIRST_NAME_EMPTY
                            else if (it[4].isEmpty())
                                ValidateDataError.LAST_NAME_EMPTY
                            else
                                ValidateDataError.OK
                        }
                    }
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun registerUser() {
        val psw = passwordChannel.value
        val user = User(email = emailChannel.value, firstname = firstNameChannel.value, lastname = lastNameChannel.value)
        viewModelScope.launch(coroutineContextProvider.IO) {
            repository.registerUser(user,psw).collect {
                withContext(coroutineContextProvider.Main) {
                    _registeredUser.value = Event(it)
                }
            }
        }
    }

}

enum class ValidateDataError(val code: Int) {
    OK(0),
    INCORRECT_EMAIL(10),
    PASSWORD_EMPTY(20),
    PASSWORD_AGAIN_EMPTY(21),
    PASSWORD_NOT_EQUAL(30),
    FIRST_NAME_EMPTY(40),
    LAST_NAME_EMPTY(50),
}