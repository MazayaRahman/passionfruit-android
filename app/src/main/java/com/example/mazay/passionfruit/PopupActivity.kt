package com.example.mazay.passionfruit

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.example.mazay.passionfruit.models.ProfileResponse
import kotlinx.android.synthetic.main.activity_popup.*

class PopupActivity : AppCompatActivity() {




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

    }
}
