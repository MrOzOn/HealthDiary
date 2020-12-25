package com.mrozon.feature_measure.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mrozon.core_api.entity.Measure
import com.mrozon.core_api.entity.MeasureHistory
import com.mrozon.core_api.entity.MeasureType
import com.mrozon.core_api.entity.Person
import com.mrozon.core_api.providers.CoroutineContextProvider
import com.mrozon.feature_measure.data.MeasureRepository
import com.mrozon.utils.Event
import com.mrozon.utils.base.BaseViewModel
import com.mrozon.utils.network.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class ListMeasureFragmentViewModel @Inject constructor(
    private val repository: MeasureRepository,
    private val coroutineContextProvider: CoroutineContextProvider
): BaseViewModel() {

    private var _initialData = MutableLiveData<Event<Result<MeasureHistory>>>()
    val initialData: LiveData<Event<Result<MeasureHistory>>>
        get() = _initialData

    private var _measures = MutableLiveData<Event<Result<List<Measure>>>>()
    val measures: LiveData<Event<Result<List<Measure>>>>
        get() = _measures

    fun initialLoadData(personId: Long, measureTypeId: Long) {
//        if(_initialData.value == null) {
            viewModelScope.launch(coroutineContextProvider.IO) {
                repository.loadMeasure(personId, measureTypeId).collect {
                    withContext(coroutineContextProvider.Main) {
                        _initialData.value = Event(it)
                    }
                }
            }
//        }
    }

    fun refreshMeasuresNetwork() {
        if(_initialData.value == null)
            return
        val personId = _initialData.value?.peekContent()?.data?.first?.id?:-1
        val measureTypeId = _initialData.value?.peekContent()?.data?.second?.id?:-1
        viewModelScope.launch(coroutineContextProvider.IO) {
            repository.loadMeasureOnlyNetwork(personId, measureTypeId).collect {
                withContext(coroutineContextProvider.Main) {
                    _measures.value = Event(it)
                }
            }
        }
    }


}