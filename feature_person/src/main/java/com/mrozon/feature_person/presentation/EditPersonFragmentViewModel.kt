package com.mrozon.feature_person.presentation

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mrozon.core_api.entity.Gender
import com.mrozon.core_api.entity.Person
import com.mrozon.feature_person.R
import com.mrozon.feature_person.data.PersonRepository
import com.mrozon.utils.Event
import com.mrozon.utils.base.BaseViewModel
import com.mrozon.utils.network.Result
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import java.util.*
import javax.inject.Inject

class EditPersonFragmentViewModel @Inject constructor(val context: Context, val repository: PersonRepository): BaseViewModel() {

    private var _male:Boolean = true

    private var _person = MutableLiveData<Event<Result<Person>>>()
    val person: LiveData<Event<Result<Person>>>
        get() = _person

    private var _initPerson = MutableLiveData<Event<Result<Person>>>()
    val initPerson: LiveData<Event<Result<Person>>>
        get() = _initPerson

    private var _deletedPerson = MutableLiveData<Event<Result<Unit>>>()
    val deletedPerson: LiveData<Event<Result<Unit>>>
        get() = _deletedPerson

    private var _sharePerson = MutableLiveData<Event<Result<Unit>>>()
    val sharePerson: LiveData<Event<Result<Unit>>>
        get() = _sharePerson

    @ExperimentalCoroutinesApi
    val personNameChannel = ConflatedBroadcastChannel<String>()

    @ExperimentalCoroutinesApi
    val personDobChannel = ConflatedBroadcastChannel<Date>()

    @ExperimentalCoroutinesApi
    @FlowPreview
    val enableAdding = object: MutableLiveData<Boolean>() {

        override fun onActive() {
            value?.let { return }
            viewModelScope.launch {
                var job: Deferred<Unit>? = null
                personNameChannel.asFlow()
                    .combine(personDobChannel.asFlow()) { name, date ->
                        Pair(name, date)
                    }
                    .collect {
                        job?.cancel()
                        job = async(Dispatchers.Main) {
                            value = it.first.isNotEmpty() and it.second.before(Date())
                        }
                    }
            }
        }
    }

    fun setMaleGender(isMale: Boolean) {
        _male = isMale
    }

    @ExperimentalCoroutinesApi
    fun addPerson() {
        var gender = Gender.MALE
        if(!_male)
            gender = Gender.FEMALE
        val personEntity = Person(name = personNameChannel.value, gender = gender, born = personDobChannel.value )
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPerson(personEntity).collect {
                withContext(Dispatchers.Main) {
                    _person.value = Event(it)
                }
            }
        }
    }

    fun initValue(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPerson(id).collect {
                withContext(Dispatchers.Main) {
                    _initPerson.value = Event(it)
                }
            }
        }
    }

    fun deletePerson(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePerson(id).collect {
                withContext(Dispatchers.Main) {
                    _deletedPerson.value = Event(it)
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun editPerson(id: Long) {
        var gender = Gender.MALE
        if(!_male)
            gender = Gender.FEMALE
        val personEntity = Person(id = id, name = personNameChannel.value, gender = gender, born = personDobChannel.value )
        viewModelScope.launch(Dispatchers.IO) {
            repository.editPerson(personEntity).collect {
                withContext(Dispatchers.Main) {
                    _person.value = Event(it)
                }
            }
        }
    }

    fun sharePersonToUser(id: Long, userName: String) {
        if(userName.isEmpty())
        {
            _sharePerson.value = Event(Result.error(context.getString(R.string.error_empty_string)))
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userName).matches())
        {
            _sharePerson.value = Event(Result.error(context.getString(R.string.error_invalid_email)))
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.sharePerson(id, userName).collect {
                withContext(Dispatchers.Main) {
                    _sharePerson.value = Event(it)
                }
            }
        }
    }

}