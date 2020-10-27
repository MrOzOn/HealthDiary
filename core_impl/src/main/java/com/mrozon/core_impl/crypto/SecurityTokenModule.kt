package com.mrozon.core_impl.crypto

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.mrozon.core_api.navigation.SplashNavigator
import com.mrozon.core_api.security.SecurityTokenService
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface SecurityTokenModule {

    @Reusable
    @Binds
    fun  provideSecurityTokenService(service: SecurityTokenServiceImpl): SecurityTokenService

}