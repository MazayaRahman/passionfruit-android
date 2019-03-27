package com.example.mazay.passionfruit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.internal.FirebaseAppHelper.getToken
import com.google.firebase.auth.GetTokenResult
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
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

        switchFragment(FeedFragment())


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

    private fun switchFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frame_layout_holder, fragment)
        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
    }
}
