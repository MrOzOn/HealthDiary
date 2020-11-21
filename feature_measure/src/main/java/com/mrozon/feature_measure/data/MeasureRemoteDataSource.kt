package com.mrozon.feature_measure.data

import com.mrozon.core_api.network.HealthDiaryService
import com.mrozon.utils.base.BaseDataSource
import javax.inject.Inject

class MeasureRemoteDataSource @Inject constructor(private val service: HealthDiaryService): BaseDataSource() {

    suspend fun getMeasure(personId: Long, measureTypeId: Long)
            = getResult {
        service.getMeasure(measureTypeId, personId)
    }

}