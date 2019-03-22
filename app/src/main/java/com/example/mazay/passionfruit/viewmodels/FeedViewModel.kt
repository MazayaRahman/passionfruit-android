package com.example.mazay.passionfruit.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.mazay.passionfruit.models.FeedResponse
import com.example.mazay.passionfruit.models.ProfileResponse
import com.example.mazay.passionfruit.service.apiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FeedViewModel: ViewModel() {
    var users: MutableLiveData<List<ProfileResponse.User>>? = null


    fun getFeed() : MutableLiveData<List<ProfileResponse.User>>?{
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.myjson.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val apiClientService = retrofit.create(apiClient::class.java)

        apiClientService.getUsers().enqueue(object: Callback<FeedResponse> {

            override fun onResponse(call: Call<FeedResponse>, response: Response<FeedResponse>) {
                if (!response.isSuccessful) {
                    // Report error here
                    Log.d("MainActivity", "feedResponse failed!")
                    return
                }
                val feedResponse = response.body()
                        ?: // Report error here
                        return
                //Log.d("MainActivity", "userdata: " +(feedResponse?.users==null))

                if(feedResponse.users != null) {
                    //Log.d("MainActivity", "feedresponse: " +feedResponse.toString())
                    users?.setValue(response.body()!!.users);

                }
            }
            override fun onFailure(call: Call<FeedResponse>, t: Throwable) {
                // Report error here
                Log.d("MainActivity", t.toString())
            }
        });

        return users
    }





}