package com.mrozon.feature_splash.data

import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.network.HealthDiaryService
import com.mrozon.core_api.network.model.PersonRequest
import com.mrozon.core_api.network.model.SharePersonRequest
import com.mrozon.utils.base.BaseDataSource
import javax.inject.Inject

class PreloadDataRemoteDataSource @Inject constructor(private val service: HealthDiaryService): BaseDataSource() {

    suspend fun getMeasureTypes()
            = getResult {
                service.getMeasureTypes()
            }

}