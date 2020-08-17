package com.mrozon.core_impl.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mrozon.core_api.db.HealthDiaryDatabaseContract
import com.mrozon.core_api.db.model.UserDb

@Database(entities = [UserDb::class], version = 1)
abstract class HabitsDatabase : RoomDatabase(), HealthDiaryDatabaseContract