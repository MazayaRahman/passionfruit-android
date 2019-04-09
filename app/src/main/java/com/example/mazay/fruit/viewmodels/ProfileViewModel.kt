package com.example.mazay.fruit.viewmodels


import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.mazay.fruit.models.FeedResponse
import com.example.mazay.fruit.models.ProfileResponse
import com.example.mazay.fruit.service.apiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ProfileViewModel : ViewModel() {
    private lateinit var userId: Integer
    private var user: MutableLiveData<ProfileResponse.User> = MutableLiveData()

    fun getUser(): MutableLiveData<ProfileResponse.User>?{
        return user
    }

    fun setUser(): LiveData<ProfileResponse.User>{
        if (user == null) {
            user = MutableLiveData<ProfileResponse.User>()
            loadUser()
        }
        return user as MutableLiveData<ProfileResponse.User>
    }

    fun loadUser(): LiveData<ProfileResponse.User> {
        userId = Integer(123123123)
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.myjson.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val apiClientService = retrofit.create(apiClient::class.java)

        apiClientService.getProfile().enqueue(object: Callback<ProfileResponse> {

            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                if (!response.isSuccessful) {
                    // Report error here
                    Log.d("MainActivity", "feedResponse failed!")
                    return
                }
                val profResponse = response.body()
                        ?: // Report error here
                        return
                //Log.d("MainActivity", "userdata: " +(feedResponse?.users==null))

                if(profResponse.user != null) {
                    //Log.d("MainActivity", "feedresponse: " +feedResponse.toString())
                    user.value = profResponse.user

                }
            }
            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                // Report error here
                Log.d("MainActivity", t.toString())
            }
        });
        return user
    }
}