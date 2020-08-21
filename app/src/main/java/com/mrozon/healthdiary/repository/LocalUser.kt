package com.mrozon.healthdiary.repository

import androidx.lifecycle.LiveData
import com.mrozon.core_api.entity.User

interface LocalUser {

    fun getLocalUser(): LiveData<User>
}