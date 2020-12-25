package com.mrozon.feature_measure.data

import com.mrozon.core_api.entity.Measure
import com.mrozon.core_api.entity.MeasureHistory
import com.mrozon.core_api.entity.MeasureType
import com.mrozon.core_api.entity.Person
import com.mrozon.utils.network.Result
import kotlinx.coroutines.flow.Flow

interface MeasureRepository {

    fun loadProfilePersonAndMeasureTypes(id: Long): Flow<Result<Pair<Person,List<MeasureType>>>>

    fun loadMeasure(personId: Long, measureTypeId: Long): Flow<Result<MeasureHistory>>

    fun loadMeasureOnlyNetwork(personId: Long, measureTypeId: Long): Flow<Result<List<Measure>>>

    fun loadSelectedPersonAndMeasureTypes(personId: Long, measureTypeId: Long): Flow<Result<Pair<Person,MeasureType>>>

    fun loadSelectedMeasure(id: Long): Flow<Result<Measure>>

    fun deleteMeasure(id: Long): Flow<Result<Unit>>

    fun addMeasure(measure: Measure): Flow<Result<Unit>>

    fun editMeasure(measure: Measure): Flow<Result<Unit>>

}