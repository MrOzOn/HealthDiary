package com.mrozon.utils.base

import retrofit2.Response
import timber.log.Timber
import com.mrozon.utils.network.Result
import org.json.JSONObject

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            //TODO response.errorBody()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.success(body)
            }
            var errMessage = response.message()
            val errorBody= response.errorBody()
            val jObjError = JSONObject(errorBody?.string()?:"")
            if(jObjError.length()>0){
                val key = jObjError.names().get(0).toString()
                errMessage = jObjError[key].toString().trim('[',']')
            }
//            return error(" ${response.code()} $errMessage")
            return error(errMessage)
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        Timber.e(message)
        return Result.error("Network error:\n $message")
//        return Result.error("Network call has failed for a following reason:\n $message")
    }

}