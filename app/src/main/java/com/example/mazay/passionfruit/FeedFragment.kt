package com.example.mazay.passionfruit

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.mazay.passionfruit.models.FeedResponse
import com.example.mazay.passionfruit.models.ProfileResponse
import com.example.mazay.passionfruit.service.apiClient
import com.example.mazay.passionfruit.viewmodels.FeedViewModel
import kotlinx.android.synthetic.main.feed_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.Toast
import android.provider.ContactsContract.CommonDataKinds.Note




class FeedFragment: Fragment() {
    //var userList: MutableList<ProfileResponse.User>? = mutableListOf()
    private lateinit var model: FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true


        model = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        model.loadUsers().observe(this, Observer{ users ->
            // update UI
            recyclerView.apply {
                // set a LinearLayoutManager to handle Android
                layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                // set the custom adapter to the RecyclerView
                Log.d("MainActivity",users.toString())
                adapter = CustomAdapter(users)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.feed_layout, container, false)

}