package com.techfort.taakri.data.remote

import com.techfort.planetapi.remot.ApiService
import com.techfort.planetapi.remot.model.UserResponse
import com.techfort.planetapi.util.PrefKey
import com.techfort.planetapi.util.PrefUtil
import io.reactivex.Flowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import retrofit2.Response

object Repository {
    val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xNjcuMTcyLjY2" +
            "LjE4Mzo4MDAwXC9hcGlcL3YxXC9sb2dpbiIsImlhdCI6MTU4MTQ0NTk0MCwibmJmIjoxNTgxNDQ1OTQw" +
            "LCJqdGkiOiJtYWhsbWl6Z0lhdHBHOUZpIiwic3ViIjoxLCJwcnYiOiI2MTM0MDBlZmIxYmZiNTBmO" +
            "TZmOTBiM2VmZjNlYjFlNDlkZjRlODFkIn0.XnQzbfBg9uLUVw_c54xo2CmlmDHxNhCqJJQy2f1PaRA"


    /* fun signIn(email: String, password: String): Flowable<Response<ResponseBody>> {
         val apiClient = ApiService.getApiClient()
         return apiClient.logIn(email, password);
     }*/

    fun register(
        email: String,
        firstName: String,
        lastName: String,
        password: String
    ): Flow<Response<ResponseBody>> {
        return flow {
            val apiClient = ApiService.getApiClient()
            val dataMap = mapOf(
                "email" to email,
                "first_name" to firstName,
                "last_name" to lastName,
                "password" to password
            )
            //val response = apiClient.register(dataMap)
            val response2 = apiClient.register2(email, firstName, lastName, password)

            emit(response2)
        }.flowOn(Dispatchers.IO)
    }

    fun login(email: String, password: String): Flow<Response<ResponseBody>> {
        return flow {
            val apiClient = ApiService.getApiClient()
            val map = mapOf<String, String>("email" to email, "password" to password)
            val response = apiClient.logIn(map)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }


    suspend fun getUserList(): Response<UserResponse> {
        val apiClient = ApiService.getApiClient()
        val map = HashMap<String, String>()
        val token = PrefUtil.get(PrefKey.ACCSEE_TOKEN, "token")
        map.put("Accept", "application/json")
        map.put("Authorization", "Bearer " + token)
        return apiClient.getUsers(map)
    }

    fun deleteUser(id: Int): Flow<Response<ResponseBody>> {
        return flow {
            val apiClient = ApiService.getApiClient()
            val map = HashMap<String, String>()
            val token = PrefUtil.get(PrefKey.ACCSEE_TOKEN, "token")
            map.put("Accept", "application/json")
            map.put("Authorization", "Bearer " + token)
            val response = apiClient.deleteUser(map, id)
            emit(response)
        }
    }

    fun getProfile(): Flowable<Response<ResponseBody>> {
        val apiClient = ApiService.getApiClient()
        val map = HashMap<String, String>()
        map.put("Accept", "application/json")
        map.put("Authorization", "Bearer " + token)
        return apiClient.getUserProfile(map)
    }

    fun getResult(): Flowable<Response<ResponseBody>> {
        val apiclient = ApiService.getApiClient()
        return apiclient.getResult()
    }

}