package com.techfort.taakri.data.remote

import com.techfort.planetapi.remot.model.UserResponse
import io.reactivex.Flowable
import okhttp3.Headers
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiClient {
    @POST("/register")
    @FormUrlEncoded
    suspend fun register(@FieldMap formValue: Map<String, String>): Response<ResponseBody>

    @POST("/register")
    @FormUrlEncoded
    suspend fun register2(
        @Field("email") email: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("password") password: String
    ): Response<ResponseBody>

    @GET("/users")
    suspend fun getUsers(@HeaderMap headers: Map<String, String>): Response<UserResponse>

    @POST("/login")
    @FormUrlEncoded
    suspend fun logIn(@FieldMap formValue: Map<String, String>): Response<ResponseBody>

    @GET("/users")
    fun getUserProfile(@HeaderMap map: HashMap<String, String>): Flowable<Response<ResponseBody>>

    @DELETE("/remove_user/{user_id}")
    suspend fun deleteUser(
        @HeaderMap map: HashMap<String, String>,
        @Path("user_id") user_id: Int
    ): Response<ResponseBody>

    @Multipart
    @POST("/planets")
    fun updateProfile(
        @HeaderMap
        map: HashMap<String, String>,
        @Query("name")
        name: String,
        @Query("email")
        email: String,
        @Query("password")
        password: String,
        @Query("profile_photo")
        @Part
        file: MultipartBody.Part
    ): Flowable<Response<ResponseBody>>

    @GET("/result")
    fun getResult(): Flowable<Response<ResponseBody>>

}