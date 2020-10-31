package com.mrozon.core_api.db.dao

import androidx.room.*
import com.mrozon.core_api.db.model.MeasureTypeDb
import com.mrozon.core_api.db.model.PersonDb

@Dao
interface MeasureTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMeasureType(persons: List<MeasureTypeDb>)

    @Query("DELETE FROM measure_type_table")
    suspend fun deleteAllMeasureType()

    @Transaction
    suspend fun reloadMeasureType(measureTypes: List<MeasureTypeDb>) {
        deleteAllMeasureType()
        insertAllMeasureType(measureTypes)
    }

}