package com.mrozon.core_api.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mrozon.core_api.db.model.PersonDb
import com.mrozon.core_api.db.model.UserDb

@Dao
interface HealthDiaryDao {

    // USER
    @Query("SELECT * FROM user_table LIMIT 1")
    fun getUser(): LiveData<UserDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userDb: UserDb)

    @Delete
    suspend fun deleteUser(userDb: UserDb)

    // TOKEN
    @Query("SELECT user_token from user_table LIMIT 1")
    fun getAccessToken(): String

    // PERSON
    @Query("SELECT * FROM person_table")
    fun getPersons(): LiveData<List<PersonDb>>

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
}