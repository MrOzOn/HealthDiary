package com.mrozon.healthdiary.repository

import androidx.lifecycle.LiveData
import com.mrozon.core_api.entity.User

interface LocalUserDataSource {

    fun getLocalUser(): LiveData<User>

    suspend fun clearLocalUser(user: User)
}