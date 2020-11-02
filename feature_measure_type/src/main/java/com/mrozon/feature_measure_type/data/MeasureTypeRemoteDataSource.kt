package com.mrozon.feature_measure_type.data

import com.mrozon.core_api.network.HealthDiaryService
import com.mrozon.utils.base.BaseDataSource
import javax.inject.Inject

class MeasureTypeRemoteDataSource @Inject constructor(private val service: HealthDiaryService): BaseDataSource() {

    suspend fun getMeasureTypes()
            = getResult {
        service.getMeasureTypes()
    }

}