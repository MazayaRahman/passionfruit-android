package com.example.mazay.fruit

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.mazay.fruit.models.ProfileResponse
import kotlinx.android.synthetic.main.activity_popup.*

class PopupActivity : AppCompatActivity() {


    private lateinit var sharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup)
        supportActionBar?.hide()
        val intent : Intent = intent
        val user = intent.extras.get("user")as ProfileResponse.User

        val metrics = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(metrics)

        getWindow().setLayout((metrics.widthPixels*.8).toInt(), (metrics.heightPixels*.7).toInt())

        val params : WindowManager.LayoutParams = getWindow().attributes
        params.gravity = Gravity.CENTER
        params.x = 0
        params.y = -20

        getWindow().attributes = params

        btn_close.setOnClickListener {
            finish()
        }
        userName.text = user.name
        userClass.text = user.year
        userMajor.text = user.major
        userBio.text = user.bio
        Glide.with(this).load(user?.photo_url).into(userprofile_image);
        //userBio.text = sharedPref.getString("user_id", "it s not working");
        var facebook = user.socials.facebook
        var insta = user.socials.instagram
        var github = user.socials.twitter

        button_fb.setOnClickListener {
            if(facebook.isNullOrEmpty()){
                Toast.makeText(this, "No Facebook linked.", Toast.LENGTH_LONG).show()
            }else{
                facebook = facebook.toString()
                Log.d("Facebook:",facebook)
                val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(facebook))
                startActivity(browserIntent)
            }

        }

        button_ig.setOnClickListener {
            if(insta.isNullOrEmpty()){
                Toast.makeText(this, "No Instagram linked.", Toast.LENGTH_LONG).show()
            }else{
                insta = insta.toString()
                val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(insta))
                startActivity(browserIntent)
            }

        }

        button_gh.setOnClickListener {
            if(github.isNullOrEmpty()){
                Toast.makeText(this, "No Github linked.", Toast.LENGTH_LONG).show()
            }else{
                github = github.toString()
                val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(github))
                startActivity(browserIntent)
            }

        }

    }
}
