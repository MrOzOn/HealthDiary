package com.mrozon.core_impl.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mrozon.core_api.db.HealthDiaryDatabaseContract
import com.mrozon.core_api.db.model.MeasureTypeDb
import com.mrozon.core_api.db.model.PersonDb
import com.mrozon.core_api.db.model.UserDb

@Database(entities = [UserDb::class, PersonDb::class, MeasureTypeDb::class], version = 1)
abstract class HealthDiaryDb : RoomDatabase(), HealthDiaryDatabaseContract