package com.mrozon.core_api.db.dao

import androidx.room.*
import com.mrozon.core_api.db.model.PersonDb
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Query("SELECT * FROM person_table")
    fun getPersons(): Flow<List<PersonDb>>

    @Query("SELECT * FROM person_table WHERE person_id=:id LIMIT 1")
    suspend fun getPerson(id: Long): PersonDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPerson(persons: List<PersonDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: PersonDb)

    @Query("DELETE FROM person_table WHERE person_id=:id")
    suspend fun deletePerson(id: Long)

    @Query("DELETE FROM person_table")
    suspend fun deleteAllPerson()

    @Transaction
    suspend fun reloadPersons(persons: List<PersonDb>) {
        deleteAllPerson()
        insertAllPerson(persons)
    }
}