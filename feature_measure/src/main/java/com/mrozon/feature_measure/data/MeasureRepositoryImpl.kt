package com.mrozon.feature_measure.data

import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.entity.Measure
import com.mrozon.core_api.entity.MeasureHistory
import com.mrozon.core_api.entity.MeasureType
import com.mrozon.core_api.entity.Person
import com.mrozon.core_api.mapper.MeasureToMeasureDbMapper
import com.mrozon.core_api.mapper.MeasureTypeToMeasureTypeDbMapper
import com.mrozon.core_api.mapper.PersonToPersonDbMapper
import com.mrozon.core_api.network.model.MeasureRequest
import com.mrozon.core_api.network.model.toMeasure
import com.mrozon.core_api.network.model.toPerson
import com.mrozon.utils.extension.toDateString
import com.mrozon.utils.network.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeasureRepositoryImpl @Inject constructor(
    private val dao: HealthDiaryDao,
    private val dataSource: MeasureRemoteDataSource,
    private val mapperPerson: PersonToPersonDbMapper,
    private val mapperMeasureType: MeasureTypeToMeasureTypeDbMapper,
    private val mapperMeasure: MeasureToMeasureDbMapper
): MeasureRepository {

    override fun loadProfilePersonAndMeasureTypes(id: Long): Flow<Result<Pair<Person, List<MeasureType>>>> {
        return flow {
            emit(Result.loading())
            try {
                val person = mapperPerson.reverseMap(dao.getPerson(id))
                val measureTypes = mapperMeasureType.reverseMap(dao.getListMeasureTypes())
                emit(Result.success(Pair(person!!, measureTypes)))
            }
            catch (e: Exception){
                emit(Result.error(e.message!!))
            }
        }
    }

    override fun loadMeasure(personId: Long, measureTypeId: Long): Flow<Result<MeasureHistory>> {
        return  flow {
            emit(Result.loading())
            try {
                val person = mapperPerson.reverseMap(dao.getPerson(personId))!!
                val measureType = mapperMeasureType.reverseMap(dao.getMeasureType(measureTypeId))!!
                val measures = mapperMeasure.reverseMap(dao.getMeasures(personId, measureTypeId))
                emit(Result.success(Triple(person, measureType, measures)))

                val networkResult = dataSource.getMeasure(personId, measureTypeId)
                if (networkResult.status == Result.Status.SUCCESS) {
                    val data = networkResult.data!!
                    val measureList = data.map { measureResponse ->
                        measureResponse.toMeasure()
                    }
                    val measuresDb = mapperMeasure.map(measureList)
                    dao.reloadMeasure(measuresDb)
                    emit(Result.success(Triple(person, measureType, measureList)))
                    } else if (networkResult.status == Result.Status.ERROR) {
                        emit(Result.error(networkResult.message!!))
                    }
                }
            catch (e: Exception){
                emit(Result.error(e.message!!))
            }
        }
    }

    override fun loadMeasureOnlyNetwork(personId: Long, measureTypeId: Long): Flow<Result<List<Measure>>> {
        return  flow {
            emit(Result.loading())
            try {
                val networkResult = dataSource.getMeasure(personId, measureTypeId)
                if (networkResult.status == Result.Status.SUCCESS) {
                    val data = networkResult.data!!
                    val measureList = data.map { measureResponse ->
                        measureResponse.toMeasure()
                    }
                    val measuresDb = mapperMeasure.map(measureList)
                    dao.reloadMeasure(measuresDb)
                    emit(Result.success(measureList))
                } else if (networkResult.status == Result.Status.ERROR) {
                    emit(Result.error(networkResult.message!!))
                }
            }
            catch (e: Exception){
                emit(Result.error(e.message!!))
            }
        }
    }

    override fun loadSelectedPersonAndMeasureTypes(
        personId: Long,
        measureTypeId: Long
    ): Flow<Result<Pair<Person, MeasureType>>> {
        return  flow {
            emit(Result.loading())
            try {
                val personDb = dao.getPerson(personId)
                val measureTypeDb = dao.getMeasureType(measureTypeId)
                emit(Result.success(Pair(mapperPerson.reverseMap(personDb)!!, mapperMeasureType.reverseMap(measureTypeDb)!!)))
            }
            catch (e: Exception){
                emit(Result.error(e.message!!))
            }
        }
    }

    override fun loadSelectedMeasure(id: Long): Flow<Result<Measure>> {
        return  flow {
            emit(Result.loading())
            try {
                val measureDb = dao.getMeasure(id)
                emit(Result.success(mapperMeasure.reverseMap(measureDb)!!))
            }
            catch (e: Exception){
                emit(Result.error(e.message!!))
            }
        }
    }

    override fun deleteMeasure(id: Long): Flow<Result<Unit>> {
        return flow {
            emit(Result.loading())
            val response = dataSource.deleteMeasure(id)
            if (response.status == Result.Status.SUCCESS) {
                dao.deleteMeasure(id)
                emit(Result.success())
            } else if (response.status == Result.Status.ERROR) {
                emit(Result.error(response.message!!))
            }
        }
    }

    override fun addMeasure(measure: Measure): Flow<Result<Unit>> {
        return flow {
            emit(Result.loading())
            val observing = dao.getSuspendUser().id
            val request = MeasureRequest (
                value1 = measure.value1,
                value2 = measure.value2,
                value_added = measure.valueAdded.toDateString(format = "yyyy-MM-dd'T'HH:mm:ss"),
                comments = measure.comment,
                type = measure.measureTypeId,
                patient = measure.personId,
                observing = observing
            )
            val response = dataSource.addMeasure(request)
            if (response.status == Result.Status.SUCCESS) {
                val measureDb = mapperMeasure.map(response.data?.toMeasure())
                dao.insertMeasure(measureDb!!)
                emit(Result.success())
            } else if (response.status == Result.Status.ERROR) {
                emit(Result.error(response.message!!))
            }
        }
    }

    override fun editMeasure(measure: Measure): Flow<Result<Unit>> {
        return flow {
            emit(Result.loading())
            val observing = dao.getSuspendUser().id
            val request = MeasureRequest (
                value1 = measure.value1,
                value2 = measure.value2,
                value_added = measure.valueAdded.toDateString(format = "yyyy-MM-dd'T'HH:mm:ss"),
                comments = measure.comment,
                type = measure.measureTypeId,
                patient = measure.personId,
                observing = observing
            )
            val response = dataSource.editMeasure(measure.id, request)
            if (response.status == Result.Status.SUCCESS) {
                val measureDb = mapperMeasure.map(response.data?.toMeasure())
                dao.insertMeasure(measureDb!!)
                emit(Result.success())
            } else if (response.status == Result.Status.ERROR) {
                emit(Result.error(response.message!!))
            }
        }
    }




}