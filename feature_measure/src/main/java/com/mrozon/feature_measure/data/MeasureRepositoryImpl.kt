package com.mrozon.feature_measure.data

import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.entity.Measure
import com.mrozon.core_api.entity.MeasureHistory
import com.mrozon.core_api.entity.MeasureType
import com.mrozon.core_api.entity.Person
import com.mrozon.core_api.mapper.MeasureToMeasureDbMapper
import com.mrozon.core_api.mapper.MeasureTypeToMeasureTypeDbMapper
import com.mrozon.core_api.mapper.PersonToPersonDbMapper
import com.mrozon.core_api.network.model.toMeasure
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


}