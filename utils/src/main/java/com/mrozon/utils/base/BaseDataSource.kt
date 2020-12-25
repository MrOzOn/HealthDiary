package com.mrozon.utils.base

import retrofit2.Response
import timber.log.Timber
import com.mrozon.utils.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                return if (body != null)
                    Result.success(body)
                else
                    Result.success()
            }
            var errMessage = response.message()
            val errorBody= response.errorBody()
            val textErrorBode = withContext(Dispatchers.IO) { errorBody?.string()?:"" }
            if(textErrorBode.startsWith("[")) {
                errMessage = textErrorBode.trim('[',']').trim('"')
            }
            else {
                val jObjError = JSONObject(textErrorBode)
                if (jObjError.length() > 0) {
                    val key = jObjError.names()?.get(0).toString()
                    errMessage = jObjError[key].toString().trim('[', ']')
                }
            }
            return error(errMessage)
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        Timber.e(message)
        return Result.error("Network error:\n $message")
    }

}