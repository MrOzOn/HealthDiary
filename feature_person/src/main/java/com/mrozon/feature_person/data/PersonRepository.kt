package com.mrozon.feature_person.data

import androidx.lifecycle.LiveData
import com.mrozon.core_api.db.model.PersonDb
import com.mrozon.core_api.entity.Person
import com.mrozon.utils.network.Result
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    fun getPersons(): LiveData<Result<List<Person>>>
    fun addPerson(person: Person): Flow<Result<Person>>
    fun getPerson(id: Long): Flow<Result<Person>>
    fun deletePerson(id: Long): Flow<Result<Unit>>
    fun editPerson(person: Person): Flow<Result<Person>>
    fun sharePerson(id: Long, userName: String): Flow<Result<Unit>>
}