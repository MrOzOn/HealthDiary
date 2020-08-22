package com.mrozon.feature_splash.repository

import androidx.lifecycle.LiveData
import com.mrozon.core_api.entity.User

interface LocalUser {

    fun getLocalUser(): LiveData<User>
}