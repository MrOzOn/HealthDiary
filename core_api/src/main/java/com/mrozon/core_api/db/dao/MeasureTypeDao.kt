package com.mrozon.core_api.db.dao

import androidx.room.*
import com.mrozon.core_api.db.model.MeasureTypeDb
import com.mrozon.core_api.db.model.PersonDb
import kotlinx.coroutines.flow.Flow

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

    @Query("SELECT * FROM measure_type_table")
    fun getMeasureTypes(): Flow<List<MeasureTypeDb>>

    @Query("SELECT * FROM measure_type_table")
    fun getListMeasureTypes(): List<MeasureTypeDb>

    @Query("SELECT * FROM measure_type_table WHERE measure_type_id=:id LIMIT 1")
    fun getMeasureType(id: Long): MeasureTypeDb

}