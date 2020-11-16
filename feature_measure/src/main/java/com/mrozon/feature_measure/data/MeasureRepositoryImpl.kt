package com.mrozon.feature_measure.data

import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.entity.MeasureType
import com.mrozon.core_api.entity.Person
import com.mrozon.core_api.mapper.MeasureTypeToMeasureTypeDbMapper
import com.mrozon.core_api.mapper.PersonToPersonDbMapper
import com.mrozon.utils.network.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeasureRepositoryImpl @Inject constructor(
    private val dao: HealthDiaryDao,
    private val mapperPerson: PersonToPersonDbMapper,
    private val mapperMeasureType: MeasureTypeToMeasureTypeDbMapper
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


}