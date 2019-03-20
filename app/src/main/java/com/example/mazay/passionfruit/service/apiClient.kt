package com.example.mazay.passionfruit.service

import com.example.mazay.passionfruit.models.FeedResponse
import com.example.mazay.passionfruit.models.LoginResponse
import com.example.mazay.passionfruit.models.ProfileResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface apiClient {

    @GET("bins/1d788u")
    abstract fun getUsers(): Call<FeedResponse>

    @GET("bins/13yfm2")
    abstract fun getProfile(userID: Integer): Call<ProfileResponse>

    @POST("bins/7xb9m")
    abstract fun userLogin(email: String, password: String): Call<LoginResponse>
}