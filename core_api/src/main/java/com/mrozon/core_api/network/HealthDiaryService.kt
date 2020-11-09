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

//    @GET("lego/themes/")
//    suspend fun getThemes(@Query("page") page: Int? = null,
//                          @Query("page_size") pageSize: Int? = null,
//                          @Query("ordering") order: String? = null): Response<ResultsResponse<LegoTheme>>
//
//    @GET("lego/sets/")
//    suspend fun getSets(@Query("page") page: Int? = null,
//                        @Query("page_size") pageSize: Int? = null,
//                        @Query("theme_id") themeId: Int? = null,
//                        @Query("ordering") order: String? = null): Response<ResultsResponse<LegoSet>>
//
//    @GET("lego/sets/{id}/")
//    suspend fun getSet(@Path("id") id: String): Response<LegoSet>
}