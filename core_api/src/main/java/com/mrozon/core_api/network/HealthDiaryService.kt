package com.mrozon.core_api.network

import com.mrozon.core_api.network.model.*
import retrofit2.Response
import retrofit2.http.*

interface HealthDiaryService {
    companion object {
//        const val ENDPOINT = "http://10.0.2.2:8000/"
        const val ENDPOINT = "https://hdb.mr-ozon-1982.tk/"
    }

    @POST("login/")
    suspend fun loginUser(@Body body: LoginRequest): Response<LoginResponse>

    @POST("register/")
    suspend fun registerUser(@Body body: RegisterRequest): Response<RegisterResponse>

    @GET("patients/")
    suspend fun getPersons(): Response<List<PersonResponse>>

    @POST("patients/")
    suspend fun addPerson(@Body body: PersonRequest): Response<PersonResponse>

    @DELETE("patients/{id}/")
    suspend fun deletePerson(@Path("id") id: String): Response<Unit>

    @PUT("patients/{id}/")
    suspend fun editPerson(@Path("id") id: String,
                           @Body body: PersonRequest): Response<PersonResponse>

    @POST("add-user-to-patient/")
    suspend fun sharePerson(@Body body: SharePersonRequest): Response<PersonResponse>

    @GET("indicatortypes/")
    suspend fun getMeasureTypes(): Response<List<MeasureTypeResponse>>

    @GET("indicators/")
    suspend fun getMeasure(@Query("type") type: Long,
                           @Query("patient") patient: Long): Response<List<MeasureResponse>>

    @DELETE("indicators/{id}/")
    suspend fun deleteMeasure(@Path("id") id: String): Response<Unit>

    @POST("indicators/")
    suspend fun addMeasure(@Body body: MeasureRequest): Response<MeasureResponse>

    @PUT("indicators/{id}/")
    suspend fun editMeasure(@Path("id") id: String,
                           @Body body: MeasureRequest): Response<MeasureResponse>
}