package com.mrozon.feature_person.data

import androidx.lifecycle.Transformations
import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.db.model.PersonDb
import com.mrozon.core_api.entity.Gender
import com.mrozon.core_api.entity.Person
import com.mrozon.core_api.mapper.PersonToPersonDbMapper
import com.mrozon.core_api.network.model.PersonRequest
import com.mrozon.core_api.network.model.SharePersonRequest
import com.mrozon.core_api.network.model.toPerson
import com.mrozon.core_api.resultLiveData
import com.mrozon.utils.extension.toDateString
import com.mrozon.utils.network.Result
import com.mrozon.utils.network.Result.Companion.error
import com.mrozon.utils.network.Result.Companion.loading
import com.mrozon.utils.network.Result.Companion.success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
            dao.deleteAllPerson()
            dao.insertAllPerson(personsDb)
        }
    )

    fun addPerson(person: Person): Flow<Result<Person>> {
        return flow {
            emit(loading())
            var gender = true
            if (person.gender==Gender.FEMALE) gender = false
            val personRequest = PersonRequest(name=person.name, gender = gender, born = person.born.toDateString() )
            val response = personRemoteDataSource.addPersons(personRequest)
            if (response.status == Result.Status.SUCCESS) {
                val personDb = mapper.map(response.data?.toPerson())
                dao.insertPerson(personDb!!)
                emit(success(response.data!!.toPerson()))
            } else if (response.status == Result.Status.ERROR) {
                emit(error(response.message!!))
            }
        }
    }

    fun getPerson(id: Long): Flow<Result<Person>> {
        return flow {
            try {
                emit(loading())
                val response = dao.getPerson(id)
                emit(success(mapper.reverseMap(response)!!))
            }
            catch (e: Exception){
                emit(error(e.message!!))
            }
        }
    }

    fun deletePerson(id: Long): Flow<Result<Unit>> {
        return flow {
            val response = personRemoteDataSource.deletePerson(id)
            if (response.status == Result.Status.SUCCESS) {
                dao.deletePerson(id)
                emit(success())
            } else if (response.status == Result.Status.ERROR) {
                emit(error(response.message!!))
            }
        }
    }

    fun editPerson(person: Person): Flow<Result<Person>> {
        return flow {
            emit(loading())
            var gender = true
            if (person.gender==Gender.FEMALE) gender = false
            val personRequest = PersonRequest(name=person.name, gender = gender, born = person.born.toDateString() )
            val response = personRemoteDataSource.editPerson(person.id, personRequest)
            if (response.status == Result.Status.SUCCESS) {
                val personDb = mapper.map(response.data?.toPerson())
                dao.insertPerson(personDb!!)
                emit(success(response.data!!.toPerson()))
            } else if (response.status == Result.Status.ERROR) {
                emit(error(response.message!!))
            }
        }
    }

    fun sharePerson(id: Long, userName: String): Flow<Result<Unit>> {
        return flow {
            emit(loading())
            val request = SharePersonRequest(patient_id = id.toInt(), username = userName)
            val response = personRemoteDataSource.sharePerson(request)
            if (response.status == Result.Status.SUCCESS) {
                emit(success())
            } else if (response.status == Result.Status.ERROR) {
                emit(error(response.message!!))
            }
        }
    }

}