package com.mrozon.feature_measure.data

import com.mrozon.core_api.network.HealthDiaryService
import com.mrozon.core_api.network.model.MeasureRequest
import com.mrozon.core_api.network.model.PersonRequest
import com.mrozon.utils.base.BaseDataSource
import javax.inject.Inject

class MeasureRemoteDataSource @Inject constructor(private val service: HealthDiaryService): BaseDataSource() {

    suspend fun getMeasure(personId: Long, measureTypeId: Long)
            = getResult {
        service.getMeasure(measureTypeId, personId)
    }

    suspend fun deleteMeasure(id: Long)
            = getResult {
        service.deleteMeasure(id.toString())
    }

    suspend fun addMeasure(request: MeasureRequest)
            = getResult {
        service.addMeasure(request)
    }

    suspend fun editMeasure(id: Long, request: MeasureRequest)
            = getResult {
        service.editMeasure(id.toString(), request)
    }

}