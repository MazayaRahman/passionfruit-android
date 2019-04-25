package com.example.mazay.fruit.viewmodels


import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.example.mazay.fruit.models.FeedResponse
import com.example.mazay.fruit.models.ProfileResponse
import com.example.mazay.fruit.service.apiClient
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ProfileViewModel(val app: Application) : AndroidViewModel(app) {

    private lateinit var userId: Integer
    private var id: String = "notempty"
    private var user: MutableLiveData<ProfileResponse.User> = MutableLiveData()


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

    fun loadUserProfile(): LiveData<ProfileResponse.User> {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://passionfruit-backend.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val apiClientService = retrofit.create(apiClient::class.java)

        id = PreferenceManager.getDefaultSharedPreferences(app).getString("user_id", "it s not working");

        apiClientService.getUserProfile(id).enqueue(object: Callback<List<ProfileResponse.User>> {

            override fun onResponse(call: Call<List<ProfileResponse.User>> , response: Response<List<ProfileResponse.User>> ) {
                if (!response.isSuccessful) {
                    // Report error here
                    Log.d("MainActivity", "feedResponse failed!")
                    return
                }
                val profResponse = response.body()
                        ?: // Report error here
                        return
                //Log.d("MainActivity", "userdata: " +(feedResponse?.users==null))

                if(profResponse.get(0) != null) {
                    //Log.d("MainActivity", "feedresponse: " +feedResponse.toString())
                    user.value = profResponse.get(0)

                }
            }
            override fun onFailure(call: Call<List<ProfileResponse.User>> , t: Throwable) {
                // Report error here
                Log.d("MainActivity", "it failed")
            }
        });
        return user
    }
}