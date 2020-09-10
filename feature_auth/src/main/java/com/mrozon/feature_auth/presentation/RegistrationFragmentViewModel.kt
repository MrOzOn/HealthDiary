package com.mrozon.feature_auth.presentation

import android.util.Patterns.EMAIL_ADDRESS
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.db.model.UserDb
import com.mrozon.core_api.entity.Person
import com.mrozon.core_api.entity.User
import com.mrozon.core_api.mapper.UserToUserDbMapper
import com.mrozon.core_api.network.model.toUserDb
import com.mrozon.feature_auth.data.UserAuthRemoteDataSource
import com.mrozon.feature_auth.data.UserAuthRepository
import com.mrozon.utils.base.BaseViewModel
import com.mrozon.utils.network.Result
//import com.mrozon.utils.extension.asFlow
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineLatest
import timber.log.Timber
import javax.inject.Inject

class RegistrationFragmentViewModel @Inject constructor(
    private val repository: UserAuthRepository
): BaseViewModel() {

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    private val _registeredUser = MutableLiveData<Result<User>?>(null)
    val registeredUser: LiveData<Result<User>?>
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
                        job = async(Dispatchers.Main) {
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
        viewModelScope.launch(Dispatchers.IO) {
            repository.registerUser(user,psw).collect {
                withContext(Dispatchers.Main) {
                    _registeredUser.value = it
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