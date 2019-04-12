package com.example.mazay.fruit.service

import com.example.mazay.fruit.models.FeedResponse
import com.example.mazay.fruit.models.LoginResponse
import com.example.mazay.fruit.models.ProfileResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface apiClient {

    @GET("bins/1d788u")
    abstract fun getUsers(): Call<FeedResponse>

    @GET("bins/vykt8")
    abstract fun getSelectedUsers(): Call<FeedResponse>

    @GET("bins/13yfm2")
    abstract fun getProfile(): Call<ProfileResponse>

    @POST("bins/7xb9m")
    abstract fun userLogin(email: String, password: String): Call<LoginResponse>
}