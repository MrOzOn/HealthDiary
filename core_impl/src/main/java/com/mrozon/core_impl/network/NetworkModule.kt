package com.mrozon.core_impl.network

import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import com.google.gson.Gson
import com.mrozon.core_api.network.HealthDiaryService
import com.mrozon.core_api.security.SecurityTokenService
import com.mrozon.core_impl.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) BODY else NONE }

    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor, security: SecurityTokenService): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor { chain ->
                val newRequestBuilder = chain.request().newBuilder()
                val token = security.loadAccessToken()
                if (token.isNotEmpty()) {
                    newRequestBuilder.addHeader("Authorization", "Token $token")
                }
                chain.proceed(newRequestBuilder.build())
            }
            .build()

    @Singleton
    @Provides
    fun provideNetworkService(okhttpClient: OkHttpClient,
                              converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, HealthDiaryService::class.java)

    private fun <T> provideService(okhttpClient: OkHttpClient,
                                   converterFactory: GsonConverterFactory, clazz: Class<T>): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }

    private fun createRetrofit(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(HealthDiaryService.ENDPOINT)
            .client(okhttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}