package com.mrozon.feature_person.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mrozon.core_api.entity.Person
import com.mrozon.core_api.entity.User
import com.mrozon.core_api.providers.CoroutineContextProvider
import com.mrozon.feature_person.data.PersonRepository
import com.mrozon.utils.Event
import com.mrozon.utils.base.BaseViewModel
import com.mrozon.utils.network.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class ListPersonFragmentViewModel @Inject constructor(
    private val repository: PersonRepository,
    private val coroutineContextProvider: CoroutineContextProvider
): BaseViewModel() {
//    val persons by lazy { repository.getPersons() }

    private val _persons = MutableLiveData<Event<Result<List<Person>>>>()
    val persons: LiveData<Event<Result<List<Person>>>>
        get() = _persons

    init {
        Timber.d("init")
        viewModelScope.launch(coroutineContextProvider.IO){
            repository.getPersons().collect {
                withContext(coroutineContextProvider.Main) {
                    _persons.value = Event(it)
                }
            }
        }
    }

    fun refreshPersons() {
        viewModelScope.launch(coroutineContextProvider.IO){
            repository.refreshPersons().collect {
                withContext(coroutineContextProvider.Main) {
                    _persons.value = Event(it)
                }
            }
        }
    }

}