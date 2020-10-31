package com.mrozon.core_api.db

import androidx.room.Dao
import com.mrozon.core_api.db.dao.MeasureTypeDao
import com.mrozon.core_api.db.dao.PersonDao
import com.mrozon.core_api.db.dao.UserDao

@Dao
interface HealthDiaryDao: UserDao, PersonDao, MeasureTypeDao