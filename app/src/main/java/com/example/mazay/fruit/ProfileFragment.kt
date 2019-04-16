package com.example.mazay.fruit

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.profile_layout, container, false)


        val editprofile_btn = v.findViewById(R.id.edit_profile) as Button
        // set on-click listener
        editprofile_btn.setOnClickListener {
            // your code to perform when the user clicks on the button
            val intent : Intent = Intent(context, CreateProfileActivity::class.java)
            //intent.putExtra("user",user as Serializable)
            startActivity(intent)
        }

        return v

    }



}