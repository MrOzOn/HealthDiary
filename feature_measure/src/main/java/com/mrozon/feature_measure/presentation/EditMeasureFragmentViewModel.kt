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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Year
import java.util.*
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


}