package com.mrozon.feature_measure.data

import com.mrozon.core_api.entity.Measure
import com.mrozon.core_api.entity.MeasureType
import com.mrozon.core_api.entity.Person
import com.mrozon.utils.network.Result
import kotlinx.coroutines.flow.Flow

interface MeasureRepository {

    fun loadProfilePersonAndMeasureTypes(id: Long): Flow<Result<Pair<Person,List<MeasureType>>>>

    fun loadMeasure(personId: Long, measureTypeId: Long): Flow<Result<List<Measure>>>
}