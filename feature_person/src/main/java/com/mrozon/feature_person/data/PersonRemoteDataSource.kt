package com.mrozon.feature_person.data

import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.network.HealthDiaryService
import com.mrozon.utils.base.BaseDataSource
import javax.inject.Inject

class PersonRemoteDataSource @Inject constructor(private val service: HealthDiaryService,
        private val dao: HealthDiaryDao): BaseDataSource() {

    suspend fun getPersons()
            = getResult {
                val token = "Token "+dao.getAccessToken()
                service.getPersons(token)
            }
}