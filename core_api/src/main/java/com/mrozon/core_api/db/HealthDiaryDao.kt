package com.mrozon.core_api.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mrozon.core_api.db.model.PersonDb
import com.mrozon.core_api.db.model.UserDb

@Dao
interface HealthDiaryDao {

    @Query("SELECT * FROM user_table LIMIT 1")
    fun getUser(): LiveData<UserDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userDb: UserDb)

    @Delete
    suspend fun deleteUser(userDb: UserDb)

    @Query("SELECT * FROM person_table")
    fun getPersons(): LiveData<PersonDb>
}