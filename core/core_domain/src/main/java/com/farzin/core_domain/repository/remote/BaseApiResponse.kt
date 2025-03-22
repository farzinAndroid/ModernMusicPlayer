package com.farzin.core_domain.repository.remote

import com.farzin.core_model.remote.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseApiResponse {

    suspend fun <T> safeApiCall(apiCall:suspend () -> Response<T>) : NetworkResult<T> =
        withContext(Dispatchers.IO){
            try {
                val response = apiCall()
                if (response.isSuccessful){
                    val body = response.body()
                    body?.let {
                        return@withContext NetworkResult.Success(body.toString(),body)
                    }
                }
                return@withContext error("code : ${response.code()} , message : ${response.message()}")
            }catch (e:Exception){
                return@withContext error(e.message ?: e.toString())
            }
        }


    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed : $errorMessage")
}