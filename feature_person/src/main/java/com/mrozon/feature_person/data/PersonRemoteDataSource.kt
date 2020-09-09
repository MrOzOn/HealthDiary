package com.mrozon.feature_person.data

import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.network.HealthDiaryService
import com.mrozon.core_api.network.model.PersonRequest
import com.mrozon.utils.base.BaseDataSource
import javax.inject.Inject

class PersonRemoteDataSource @Inject constructor(private val service: HealthDiaryService,
        private val dao: HealthDiaryDao): BaseDataSource() {

    suspend fun getPersons()
            = getResult {
                val token = "Token "+dao.getAccessToken()
                service.getPersons(token)
            }

    suspend fun addPersons(personRequest: PersonRequest)
            = getResult {
        val token = "Token "+dao.getAccessToken()
        service.addPerson(token, personRequest)
    }

    suspend fun deletePerson(id: Long)
            = getResult {
        val token = "Token "+dao.getAccessToken()
        service.deletePerson(token, id.toString())
    }

    suspend fun editPerson(id: Long, personRequest: PersonRequest)
            = getResult {
        val token = "Token "+dao.getAccessToken()
        service.editPerson(token, id.toString(), personRequest)
    }
}