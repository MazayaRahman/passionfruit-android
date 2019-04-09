package com.example.mazay.fruit

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mazay.fruit.viewmodels.ProfileViewModel
import kotlinx.android.synthetic.main.profile_layout.*

class ProfileFragment: Fragment() {

    private lateinit var model: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        model = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        //model.setUser()
        model.loadUser().observe(this, Observer { user->
            Log.d("MainActivity",user?.name)
            nameView.text = user?.name
        })


    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.profile_layout, container, false)


}