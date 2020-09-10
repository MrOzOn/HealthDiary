package com.mrozon.healthdiary.data

import androidx.lifecycle.LiveData
import com.mrozon.core_api.entity.User

interface UserRepository {

    fun getLocalUser(): LiveData<User>

    suspend fun clearLocalUser(user: User)
}