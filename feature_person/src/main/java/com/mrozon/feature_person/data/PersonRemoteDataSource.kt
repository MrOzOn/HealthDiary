package com.mrozon.feature_person.data

import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.network.HealthDiaryService
import com.mrozon.core_api.network.model.PersonRequest
import com.mrozon.core_api.network.model.SharePersonRequest
import com.mrozon.utils.base.BaseDataSource
import javax.inject.Inject

class PersonRemoteDataSource @Inject constructor(private val service: HealthDiaryService,
        private val dao: HealthDiaryDao): BaseDataSource() {

    suspend fun getPersons()
            = getResult {
                service.getPersons()
            }

    suspend fun addPersons(personRequest: PersonRequest)
            = getResult {
        service.addPerson(personRequest)
    }

    suspend fun deletePerson(id: Long)
            = getResult {
        service.deletePerson(id.toString())
    }

    suspend fun editPerson(id: Long, personRequest: PersonRequest)
            = getResult {
        service.editPerson(id.toString(), personRequest)
    }

    suspend fun sharePerson(sharePersonRequest: SharePersonRequest)
            = getResult {
        service.sharePerson(sharePersonRequest)
    }
}