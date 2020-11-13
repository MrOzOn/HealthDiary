package com.mrozon.feature_measure.data

import com.mrozon.core_api.entity.Person
import com.mrozon.utils.network.Result
import kotlinx.coroutines.flow.Flow

interface MeasureRepository {

    fun getPerson(id: Long): Flow<Result<Person>>
}