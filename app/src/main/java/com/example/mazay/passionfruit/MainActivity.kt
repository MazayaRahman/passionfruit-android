package com.example.mazay.passionfruit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.internal.FirebaseAppHelper.getToken
import com.google.firebase.auth.GetTokenResult
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.example.mazay.passionfruit.models.FeedResponse
import com.example.mazay.passionfruit.models.ProfileResponse
import com.example.mazay.passionfruit.service.apiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recView = recyclerView

        recView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        var userList: MutableList<ProfileResponse.User>? = mutableListOf()

        recView.adapter = CustomAdapter(userList)

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
                    userList?.clear()
                    userList?.addAll(feedResponse.users!!)
                    recyclerView.adapter?.notifyDataSetChanged()
                    //mainText.text = userList.toString()
                    //Log.d("MainActivity", userList.toString())

                }
            }
            override fun onFailure(call: Call<FeedResponse>, t: Throwable) {
                // Report error here
                Log.d("MainActivity", t.toString())
            }
        });


 /*       val mUser = FirebaseAuth.getInstance().currentUser
        mUser!!.getIdToken(true)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val idToken = task.result!!.token
                        // Send token to your backend via HTTPS
                        // ...
                    } else {
                        // Handle error -> task.getException();
                    }
                }
*/
    }
}
