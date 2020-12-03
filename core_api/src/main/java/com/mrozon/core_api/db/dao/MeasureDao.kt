package com.mrozon.core_api.db.dao

import androidx.room.*
import com.mrozon.core_api.db.model.MeasureDb
import com.mrozon.core_api.db.model.MeasureTypeDb
import com.mrozon.core_api.db.model.PersonDb
import kotlinx.coroutines.flow.Flow

@Dao
interface MeasureDao {

    @Query("SELECT * FROM measure_table WHERE measure_person=:personId AND measure_mtype=:measureTypeId ORDER BY measure_value_added DESC LIMIT 100")
    fun getMeasures(personId: Long, measureTypeId: Long): List<MeasureDb>

    @Query("SELECT * FROM measure_table WHERE measure_id=:id LIMIT 1")
    fun getMeasure(id: Long): MeasureDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMeasure(measures: List<MeasureDb>)

    @Query("DELETE FROM measure_table")
    suspend fun deleteAllMeasure()

    @Transaction
    suspend fun reloadMeasure(measures: List<MeasureDb>) {
        deleteAllMeasure()
        insertAllMeasure(measures)
    }

    @Query("DELETE FROM measure_table WHERE measure_id=:id")
    suspend fun deleteMeasure(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeasure(measureDb: MeasureDb)
}