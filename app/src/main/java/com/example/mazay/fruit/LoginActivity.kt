package com.example.mazay.fruit

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import java.io.Serializable

class LoginActivity : AppCompatActivity() {

    val RC_SIGN_IN = 0
    var isCreate = false
    lateinit var auth : FirebaseAuth
    lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        //auth.currentUser.email

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)



        button_gsignin.setOnClickListener {
            googleSignIn()
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent : Intent = Intent(this, MainActivity::class.java)
            //intent.putExtra("user",user as Serializable)
            startActivity(intent)
            //successCallback(currentUser)
            //callback to update ui with current user info
        }
    }



    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    fun successCallback(user : FirebaseUser) {

        textview_curr_user.text = "Hello ${user?.email}"
        button_gsignin.visibility = View.INVISIBLE

    }

    fun failCallback() {
        Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                //firebaseAuthWithGoogle(account!!)
                val email = account?.email
                if (email?.endsWith("rutgers.edu",true)!!){
                    firebaseAuthWithGoogle(account!!)
                }else{
                    Toast.makeText(this, "Please use a rutgers email.", Toast.LENGTH_LONG).show()
                    googleSignInClient.signOut()
                }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.d(javaClass.name, "Google sign in failed", e)
            }

        }

    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(javaClass.name, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)

        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(javaClass.name, "signInWithCredential:success")
                        if(task.result?.additionalUserInfo?.isNewUser!!){
                            //launch new user activity here
                            Toast.makeText(this, "New User!", Toast.LENGTH_LONG).show()
                            val intent : Intent = Intent(this, CreateProfileActivity::class.java)
                            //intent.putExtra("user",user as Serializable)
                            startActivity(intent)
                        }else{
                            val intent : Intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                        //successCallback(auth.currentUser!!)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(javaClass.name, "signInWithCredential:failure", task.exception)

                        Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_LONG).show()

                    }

                }

    }


}
