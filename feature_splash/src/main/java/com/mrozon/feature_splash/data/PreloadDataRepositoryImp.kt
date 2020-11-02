package com.mrozon.feature_splash.data

import android.content.Context
import android.graphics.Insets.add
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.entity.User
import com.mrozon.core_api.mapper.MeasureTypeToMeasureTypeDbMapper
import com.mrozon.core_api.mapper.UserToUserDbMapper
import com.mrozon.core_api.network.HealthDiaryService.Companion.ENDPOINT
import com.mrozon.core_api.network.model.toMeasureType
import com.mrozon.core_api.network.model.toPerson
import com.mrozon.utils.network.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreloadDataRepositoryImp @Inject constructor(
    private val dao: HealthDiaryDao,
    private val remoteDataSource: PreloadDataRemoteDataSource,
    private val mapper: UserToUserDbMapper,
    private val mapperMT: MeasureTypeToMeasureTypeDbMapper,
    private val context: Context
): PreloadDataRepository {

//    private val imageLoader = ImageLoader.Builder(context)
//        .componentRegistry {
//            add(SvgDecoder(context))
//        }
//        .build()

    override fun getPreloadData(): Flow<Result<User?>> {
        return flow {
            emit(Result.loading())
            val networkResult = remoteDataSource.getMeasureTypes()
            if (networkResult.status == Result.Status.SUCCESS) {
                val data = networkResult.data!!
                val measureTypes = data.map { measureType ->
                    preloadNetworkImage(measureType.url)
                    measureType.toMeasureType()
                }
                val measureTypesDb = mapperMT.map(measureTypes)
                dao.reloadMeasureType(measureTypesDb)
            }
            val query = dao.getUser()
            val userDb = query.firstOrNull()
            if(userDb==null){
                emit(Result.success(null))
            }
            else
            {
                emit(Result.success(mapper.reverseMap(userDb)))
            }
        }
    }

    private fun preloadNetworkImage(url: String){
        val imageLoader = context.imageLoader
        val request = ImageRequest.Builder(context)
            .data(ENDPOINT+url)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .build()
        imageLoader.enqueue(request)
    }

}