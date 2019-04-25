package com.example.mazay.fruit.service

import com.example.mazay.fruit.models.*
import retrofit2.Call
import com.google.gson.JsonObject
import retrofit2.http.*
import java.util.*


interface apiClient {

    @GET("users")
    abstract fun users(): Call<FeedResponse>

    @GET("users")
    abstract fun getUsersByMajor(@Query("major") major: String): Call<FeedResponse>

    @GET("users")
    abstract fun getUsersByYear(@Query("year") year: String): Call<FeedResponse>

    @GET("users")
    abstract fun getSelectedUsers(@Query("major") major: String, @Query("year") year: String): Call<FeedResponse>

    @GET("majors")
    abstract fun getMajors() : Call<MajorResponse>

    @GET("years")
    abstract fun getYears() : Call<YearResponse>

    @POST("users")
    fun saveUserData(@Body userData: ProfileResponse.User): Call<LoginResponse>


    @GET("users/{id}")
    abstract fun getUserProfile(@Path("id") userId: String): Call<List<ProfileResponse.User>>

    @GET("bins/13yfm2")
    abstract fun getProfile(): Call<ProfileResponse>

    @POST("bins/7xb9m")
    abstract fun userLogin(email: String, password: String): Call<LoginResponse>
}