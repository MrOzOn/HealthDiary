package com.mrozon.feature_person.data

import androidx.lifecycle.Transformations
import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.mapper.PersonToPersonDbMapper
import com.mrozon.core_api.network.model.toPerson
import com.mrozon.core_api.resultLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonRepository @Inject constructor(private val dao: HealthDiaryDao,
        private val personRemoteDataSource: PersonRemoteDataSource,
        private val mapper: PersonToPersonDbMapper
) {

    fun getPersons() = resultLiveData(
        databaseQuery = {
            val personDb = dao.getPersons()
            Transformations.map(personDb) {
                mapper.reverseMap(it)
            }
        },
        networkCall = { personRemoteDataSource.getPersons() },
        saveCallResult = {
            val persons = it.map { personResponse ->
                personResponse.toPerson()
            }
            val personsDb = mapper.map(persons)
            dao.insertAllPerson(personsDb)
        }
    )

}