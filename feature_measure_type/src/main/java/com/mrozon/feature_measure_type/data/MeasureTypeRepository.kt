package com.mrozon.feature_measure_type.data

import com.mrozon.core_api.entity.MeasureType
import com.mrozon.utils.network.Result
import kotlinx.coroutines.flow.Flow

interface MeasureTypeRepository {
    fun getMeasureTypes(): Flow<Result<List<MeasureType>>>
    fun refreshMeasureTypes(): Flow<Result<List<MeasureType>>>
}