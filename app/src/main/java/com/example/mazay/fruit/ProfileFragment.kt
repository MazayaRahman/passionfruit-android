package com.example.mazay.fruit

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.mazay.fruit.viewmodels.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.profile_layout.*
import com.google.gson.JsonObject



class ProfileFragment: Fragment() {

    private lateinit var model: ProfileViewModel
    lateinit var auth : FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    var userid: String = String()
    var facebook : String = String()
    var insta: String = String()
    var github: String = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true


        model = ViewModelProviders.of(this).get(ProfileViewModel::class.java)

        //model.setUser()

        model.loadUserProfile().observe(this, Observer { user->
            Log.d("MainActivity",user?.name)
            facebook = user?.socials?.facebook!!
            insta = user?.socials?.instagram!!
            github = user?.socials?.twitter!!
            majorView.text = user?.major
            classView.text = user?.year
            userBio.text = user?.bio
            nameView.text = user?.name

            Glide.with(this).load(user?.photo_url).into(profile_image);

        })





    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.profile_layout, container, false)

        val name_view = v.findViewById(R.id.nameView) as TextView
        val major_view = v.findViewById(R.id.majorView) as TextView
        auth = FirebaseAuth.getInstance()


        //name_view.text = auth.currentUser!!.email
        //major_view.text = userid

        val editprofile_btn = v.findViewById(R.id.edit_profile) as Button
        val fbButton = v.findViewById(R.id.button_fb) as Button
        val igButton = v.findViewById(R.id.button_ig) as Button
        val ghButton = v.findViewById(R.id.button_gh) as Button
        // set on-click listener
        editprofile_btn.setOnClickListener {
            // your code to perform when the user clicks on the button
            val intent : Intent = Intent(context, CreateProfileActivity::class.java)
            //intent.putExtra("user",user as Serializable)
            startActivity(intent)
        }

        fbButton.setOnClickListener {
            if(facebook.isNullOrEmpty()){
                Toast.makeText(context, "No Facebook linked.", Toast.LENGTH_LONG).show()
            }else{
                facebook = facebook.toString()
                Log.d("Facebook:",facebook)
                val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(facebook))
                startActivity(browserIntent)
            }

        }

        igButton.setOnClickListener {
            if(insta.isNullOrEmpty()){
                Toast.makeText(context, "No Instagram linked.", Toast.LENGTH_LONG).show()
            }else{
                insta = insta.toString()
                val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(insta))
                startActivity(browserIntent)
            }

        }

        ghButton.setOnClickListener {
            if(github.isNullOrEmpty()){
                Toast.makeText(context, "No Github linked.", Toast.LENGTH_LONG).show()
            }else{
                github = github.toString()
                val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(github))
                startActivity(browserIntent)
            }

        }





        return v

    }



}