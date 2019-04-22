package com.example.mazay.fruit.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    internal lateinit var sRetrofit: Retrofit
    internal var BASE_URL = "https://passionfruit-backend.herokuapp.com/"
    //internal var BASE_URL = "https://api.myjson.com/"

    public fun getRetrofit(): Retrofit {
        if (sRetrofit == null) {
            sRetrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
        }
        return sRetrofit
    }

    public fun getService(): apiClient {
        return getRetrofit().create(apiClient::class.java)
    }
}