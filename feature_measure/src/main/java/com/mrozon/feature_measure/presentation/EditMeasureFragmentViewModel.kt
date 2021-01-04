package com.mrozon.feature_measure.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mrozon.core_api.entity.Measure
import com.mrozon.core_api.entity.MeasureType
import com.mrozon.core_api.entity.Person
import com.mrozon.core_api.providers.CoroutineContextProvider
import com.mrozon.feature_measure.data.MeasureRepository
import com.mrozon.utils.Event
import com.mrozon.utils.base.BaseViewModel
import com.mrozon.utils.network.Result
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import java.time.Year
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject

class EditMeasureFragmentViewModel @Inject constructor(
    private val repository: MeasureRepository,
    private val coroutineContextProvider: CoroutineContextProvider
): BaseViewModel() {

    private var _measureType = MutableLiveData<MeasureType>()
    val measureType: LiveData<MeasureType>
        get() = _measureType

    private var _person = MutableLiveData<Person>()
    val person: LiveData<Person>
        get() = _person

    private var _measure = MutableLiveData<Event<Result<Measure>>>()
    val measure: LiveData<Event<Result<Measure>>>
        get() = _measure

    private var _currentDatetime = MutableLiveData<Date>()
    val currentDatetime: LiveData<Date>
        get() = _currentDatetime

    private var _goToListMeasure = MutableLiveData<Event<Result<Unit>>>()
    val goToListMeasure: LiveData<Event<Result<Unit>>>
        get() = _goToListMeasure

    val currentHour: Int
        get() {
            val currentCalendar = Calendar.getInstance()
            currentCalendar.time = _currentDatetime.value!!
            return currentCalendar.get(Calendar.HOUR_OF_DAY)
        }

    val currentMinute: Int
        get() {
            val currentCalendar = Calendar.getInstance()
            currentCalendar.time = _currentDatetime.value!!
            return currentCalendar.get(Calendar.MINUTE)
        }

    @ExperimentalCoroutinesApi
    val commentChannel = ConflatedBroadcastChannel<String>("")

    @ExperimentalCoroutinesApi
    val measureValueChannel = ConflatedBroadcastChannel<String>()

    @ExperimentalCoroutinesApi
    @FlowPreview
    val correctMeasureValue = object: MutableLiveData<Boolean>() {

        override fun onActive() {
            value?.let { return }
            viewModelScope.launch {
                var job: Deferred<Unit>? = null
                measureValueChannel.asFlow()
                    .collect {
                        job?.cancel()
                        job = async(Dispatchers.Main) {
                            val pattern = Pattern.compile(measureType.value?.regexp?:"")
                            val matcher = pattern.matcher(it)
                            value = matcher.matches()
                        }
                    }
            }
        }
    }

    fun initialLoadData(id: Long, personId: Long, measureTypeId: Long) {
        if(_measureType.value==null){
//            _measure.value = Event(Result.loading())
            viewModelScope.launch(coroutineContextProvider.IO) {
                repository.loadSelectedPersonAndMeasureTypes(personId, measureTypeId).collect {
                    withContext(coroutineContextProvider.Main) {
                        if(it.status== Result.Status.SUCCESS) {
                            _measureType.value = it.data?.second!!
                            _person.value = it.data?.first!!
                        }
                    }
                }
                if (id>0){
                    repository.loadSelectedMeasure(id).collect {
                        withContext(coroutineContextProvider.Main) {
                            _measure.value = Event(it)
                            if (it.status == Result.Status.SUCCESS) {
                                _currentDatetime.value = it.data?.valueAdded
                            }
                        }
                    }
                } else {
                    withContext(coroutineContextProvider.Main) {
                        _currentDatetime.value = Date()
                    }
                }
            }
//            _measure.value = Event(Result.success())
        }
    }

    fun changeDate(value: Long?) {
        value?.let {
            val currentCalendar = Calendar.getInstance()
            currentCalendar.time = _currentDatetime.value!!
            val newDate = Calendar.getInstance()
            newDate.time = Date(value)
            currentCalendar.set(Calendar.YEAR,newDate.get(Calendar.YEAR))
            currentCalendar.set(Calendar.MONTH,newDate.get(Calendar.MONTH))
            currentCalendar.set(Calendar.DAY_OF_YEAR,newDate.get(Calendar.DAY_OF_YEAR))
            _currentDatetime.value = currentCalendar.time
        }
    }

    fun changeTime(hour: Int, minute: Int) {
        val currentCalendar = Calendar.getInstance()
        currentCalendar.time = _currentDatetime.value!!
        currentCalendar.set(Calendar.HOUR_OF_DAY,hour)
        currentCalendar.set(Calendar.MINUTE,minute)
        currentCalendar.set(Calendar.SECOND,0)
        _currentDatetime.value = currentCalendar.time
    }

    fun deleteMeasure() {
        _measure.value?.let {
            viewModelScope.launch(coroutineContextProvider.IO) {
                val id = _measure.value?.peekContent()?.data?.id?:0
                repository.deleteMeasure(id).collect {
                    withContext(Dispatchers.Main) {
                        _goToListMeasure.value = Event(it)
                    }
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun saveMeasure(id: Long) {
        val newMeasureValue = measureValueChannel.value
        var value1 = newMeasureValue
        var value2 = ""
        if(newMeasureValue.contains('/')) {
            val splits = newMeasureValue.split('/')
            if(splits.size>1){
                value1 = splits[0]
                value2 = splits[1]
            }
        }

        viewModelScope.launch(coroutineContextProvider.IO) {
            val personId = _person.value?.id?:0
            val measureTypeId = _measureType.value?.id?:0
            val measure = Measure(id = id, personId = personId, measureTypeId = measureTypeId,
                comment = commentChannel.value, valueAdded = _currentDatetime.value!!,
                value1 = value1, value2 = value2
            )
            if(id>0) {
                repository.editMeasure(measure).collect {
                    withContext(Dispatchers.Main) {
                        _goToListMeasure.value = Event(it)
                    }
                }
            } else {
                repository.addMeasure(measure).collect {
                    withContext(Dispatchers.Main) {
                        _goToListMeasure.value = Event(it)
                    }
                }
            }
        }

    }


}