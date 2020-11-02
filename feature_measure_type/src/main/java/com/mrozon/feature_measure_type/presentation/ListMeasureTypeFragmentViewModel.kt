package com.mrozon.feature_measure_type.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mrozon.core_api.entity.MeasureType
import com.mrozon.core_api.entity.Person
import com.mrozon.core_api.providers.CoroutineContextProvider
import com.mrozon.feature_measure_type.data.MeasureTypeRepository
import com.mrozon.utils.Event
import com.mrozon.utils.base.BaseViewModel
import com.mrozon.utils.network.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class ListMeasureTypeFragmentViewModel @Inject constructor(
    private val repository: MeasureTypeRepository,
    private val coroutineContextProvider: CoroutineContextProvider
): BaseViewModel() {

    private val _measure_types = MutableLiveData<Event<Result<List<MeasureType>>>>()
    val measure_types: LiveData<Event<Result<List<MeasureType>>>>
        get() = _measure_types

    init {
        Timber.d("init")
        viewModelScope.launch(coroutineContextProvider.IO){
            repository.getMeasureTypes().collect {
                withContext(coroutineContextProvider.Main) {
                    _measure_types.value = Event(it)
                }
            }
        }
    }

    fun refreshMeasureTypes() {
        viewModelScope.launch(coroutineContextProvider.IO){
            repository.refreshMeasureTypes().collect {
                withContext(coroutineContextProvider.Main) {
                    _measure_types.value = Event(it)
                }
            }
        }
    }

}