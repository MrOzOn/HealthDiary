package com.mrozon.feature_splash.data

import androidx.lifecycle.LiveData
import com.mrozon.core_api.entity.User
import com.mrozon.utils.network.Result
import kotlinx.coroutines.flow.Flow

interface PreloadDataRepository {

    fun getPreloadData(): Flow<Result<User?>>
}