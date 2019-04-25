package com.example.mazay.fruit.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.mazay.fruit.models.FeedResponse
import com.example.mazay.fruit.models.MajorResponse
import com.example.mazay.fruit.models.ProfileResponse
import com.example.mazay.fruit.service.apiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.mazay.fruit.models.ProfileResponse.User
import com.example.mazay.fruit.models.YearResponse
import java.time.Year


class FeedViewModel: ViewModel() {
    //var users: MutableLiveData<List<ProfileResponse.User>>? = null
    private var users: MutableLiveData<List<User>> = MutableLiveData()
    private lateinit var model: FeedViewModel
    //private var majorList: List<String>? = null
    private var majorList: List<String> = ArrayList()
    private var yearList: List<String> = ArrayList()
    fun getfeed(): MutableLiveData<List<User>>? {
        return users
    }

    fun getUsers(): LiveData<List<User>> {
        if (users == null) {
            users = MutableLiveData<List<User>>()
            loadUsers()
        }
        return users as MutableLiveData<List<User>>
    }


    fun loadUsers() : MutableLiveData<List<ProfileResponse.User>>{
        val retrofit = Retrofit.Builder()
                .baseUrl("https://passionfruit-backend.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val apiClientService = retrofit.create(apiClient::class.java)

        apiClientService.users().enqueue(object: Callback<FeedResponse> {

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
                    users.setValue(response.body()!!.users);

                }
            }
            override fun onFailure(call: Call<FeedResponse>, t: Throwable) {
                // Report error here
                Log.d("MainActivity", t.toString())
            }
        });

        return users
    }

    fun loadSelectedUsers(major: String, year: String): MutableLiveData<List<ProfileResponse.User>>{
        val retrofit = Retrofit.Builder()
                .baseUrl("https://passionfruit-backend.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val apiClientService = retrofit.create(apiClient::class.java)

        apiClientService.getSelectedUsers(major, year).enqueue(object: Callback<FeedResponse> {

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
                    users.setValue(response.body()!!.users);

                }
            }
            override fun onFailure(call: Call<FeedResponse>, t: Throwable) {
                // Report error here
                Log.d("MainActivity", t.toString())
            }
        });

        return users

    }

    fun loadUsersByMajors(major: String): MutableLiveData<List<ProfileResponse.User>>{
        val retrofit = Retrofit.Builder()
                .baseUrl("https://passionfruit-backend.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val apiClientService = retrofit.create(apiClient::class.java)

        apiClientService.getUsersByMajor(major).enqueue(object: Callback<FeedResponse> {

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
                    users.setValue(response.body()!!.users);

                }
            }
            override fun onFailure(call: Call<FeedResponse>, t: Throwable) {
                // Report error here
                Log.d("MainActivity", t.toString())
            }
        });

        return users

    }

    fun loadUsersByYears(year: String): MutableLiveData<List<ProfileResponse.User>>{
        val retrofit = Retrofit.Builder()
                .baseUrl("https://passionfruit-backend.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val apiClientService = retrofit.create(apiClient::class.java)

        apiClientService.getUsersByYear(year).enqueue(object: Callback<FeedResponse> {

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
                    users.setValue(response.body()!!.users);

                }
            }
            override fun onFailure(call: Call<FeedResponse>, t: Throwable) {
                // Report error here
                Log.d("MainActivity", t.toString())
            }
        });

        return users

    }

    fun loadYears() : List<String>{
        val retrofit = Retrofit.Builder()
                .baseUrl("https://passionfruit-backend.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val apiClientService = retrofit.create(apiClient::class.java)

        apiClientService.getYears().enqueue(object: Callback<YearResponse> {

            override fun onResponse(call: Call<YearResponse>, response: Response<YearResponse>) {
                if (!response.isSuccessful) {
                    // Report error here
                    Log.d("MainActivity", "yearResponse failed!")
                    return
                }
                val yearResponse = response.body()
                        ?: // Report error here
                        return
                //Log.d("MainActivity", "userdata: " +(feedResponse?.users==null))

                if(yearResponse.years != null) {
                    //Log.d("MainActivity", "feedresponse: " +feedResponse.toString())
                    yearList = response.body()!!.years!!

                }
            }
            override fun onFailure(call: Call<YearResponse>, t: Throwable) {
                // Report error here
                Log.d("MainActivity", t.toString())
            }
        });

        return yearList
    }

    fun loadMajors() : List<String>{
        val retrofit = Retrofit.Builder()
                .baseUrl("https://passionfruit-backend.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val apiClientService = retrofit.create(apiClient::class.java)

        apiClientService.getMajors().enqueue(object: Callback<MajorResponse> {

            override fun onResponse(call: Call<MajorResponse>, response: Response<MajorResponse>) {
                if (!response.isSuccessful) {
                    // Report error here
                    Log.d("MainActivity", "majorResponse failed!")
                    return
                }
                val majorResponse = response.body()
                        ?: // Report error here
                        return
                //Log.d("MainActivity", "userdata: " +(feedResponse?.users==null))

                if(majorResponse.majors != null) {
                    //Log.d("MainActivity", "feedresponse: " +feedResponse.toString())
                    Log.d("MainActivity", "majorResponse:" + response.body().toString())
                    majorList = response.body()!!.majors!!
                }
            }
            override fun onFailure(call: Call<MajorResponse>, t: Throwable) {
                // Report error here
                Log.d("MainActivity", t.toString())
            }
        });

        return majorList

    }
/*
    fun loadSelectedUsers() : MutableLiveData<List<ProfileResponse.User>>{
        val retrofit = Retrofit.Builder()
                .baseUrl("https://passionfruit-backend.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val apiClientService = retrofit.create(apiClient::class.java)

        apiClientService.getSelectedUsers().enqueue(object: Callback<FeedResponse> {

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
                    Log.d("MainActivity", "feedResults:" + response.body().toString())
                    users.setValue(response.body()!!.users);

                }
            }
            override fun onFailure(call: Call<FeedResponse>, t: Throwable) {
                // Report error here
                Log.d("MainActivity", t.toString())
            }
        });

        return users
    }
*/



}