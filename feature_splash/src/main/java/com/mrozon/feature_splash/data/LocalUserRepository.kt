package com.mrozon.feature_splash.data

import androidx.lifecycle.LiveData
import com.mrozon.core_api.entity.User

interface LocalUserRepository {

    fun getLocalUser(): LiveData<User>
}