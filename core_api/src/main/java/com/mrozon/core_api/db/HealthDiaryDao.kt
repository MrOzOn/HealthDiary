package com.mrozon.core_api.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mrozon.core_api.db.model.UserDb

@Dao
interface HealthDiaryDao {

    @Query("SELECT * FROM user_table LIMIT 1")
    fun getUser(): LiveData<UserDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userDb: UserDb)
}