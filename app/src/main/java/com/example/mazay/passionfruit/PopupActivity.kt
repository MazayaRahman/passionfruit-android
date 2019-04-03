package com.example.mazay.passionfruit

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.mazay.passionfruit.models.ProfileResponse
import kotlinx.android.synthetic.main.activity_popup.*

class PopupActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup)

        val intent : Intent = intent
        val user = intent.extras.get("user")as ProfileResponse.User

        testView.text = user.name
    }
}
