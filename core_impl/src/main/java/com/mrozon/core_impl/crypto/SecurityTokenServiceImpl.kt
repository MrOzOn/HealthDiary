package com.mrozon.core_impl.crypto

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.mrozon.core_api.security.SecurityTokenProvider
import com.mrozon.core_api.security.SecurityTokenService
import javax.inject.Inject

class SecurityTokenServiceImpl @Inject constructor(
    context: Context
): SecurityTokenService {

    companion object {
        const val FILE_NAME = "access_token"
        const val FIELD_NAME = "token"
        const val EMPTY_VALUE = ""

    }

    private var ssp: SharedPreferences

    init {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        ssp = EncryptedSharedPreferences.create(
            context,
            FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun loadAccessToken(): String {
        return ssp.getString(FIELD_NAME,EMPTY_VALUE)?:EMPTY_VALUE
    }

    override fun saveAccessToken(string: String) {
        ssp.edit()
            .putString(FIELD_NAME,string)
            .apply()
    }

    override fun clearAccessToken() {
        saveAccessToken(EMPTY_VALUE)
    }
}