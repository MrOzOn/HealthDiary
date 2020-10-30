package com.mrozon.core_api.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mrozon.core_api.db.model.UserDb
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table LIMIT 1")
    fun getUser(): Flow<UserDb>

    @Query("SELECT * FROM user_table LIMIT 1")
    fun getLiveUser(): LiveData<UserDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userDb: UserDb)

    @Delete
    suspend fun deleteUser(userDb: UserDb)
}