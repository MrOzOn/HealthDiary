package com.mrozon.feature_measure.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
import javax.inject.Inject

class EditMeasureFragmentViewModel @Inject constructor(
    private val repository: MeasureRepository,
    private val coroutineContextProvider: CoroutineContextProvider
): BaseViewModel() {


}