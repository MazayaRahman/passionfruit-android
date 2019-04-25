package com.example.mazay.fruit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import de.hdodenhof.circleimageview.CircleImageView
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.provider.MediaStore
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.mazay.fruit.models.FeedResponse
import com.example.mazay.fruit.models.LoginResponse
import com.example.mazay.fruit.models.ProfileResponse
import com.example.mazay.fruit.service.apiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.JsonObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlinx.android.synthetic.main.activity_create_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable

class CreateProfileActivity : AppCompatActivity() {
    //private var imageview: CircleImageView? = null
    private val GALLERY = 1
    private val CAMERA = 2
    lateinit var auth : FirebaseAuth
    private var uId : String = String()
    private lateinit var sharedPref : SharedPreferences
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    //private var saveBtn: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)

        //imageview = findViewById(R.id.profile_image) as CircleImageView

        //imageview!!.setOnClickListener { showPictureDialog() }
        sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        auth = FirebaseAuth.getInstance()
        val photoURL = auth.currentUser!!.photoUrl.toString()
        //saveBtn = findViewById(R.id.save_btn) as Button

        // get reference to button
        val saveBtn = findViewById(R.id.save_btn) as Button
        // set on-click listener
        saveBtn.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this, "You clicked me.", Toast.LENGTH_SHORT).show()

            val usernameOfUser = editUserName.text.toString()
            val nameOfUser = editName.text.toString()
            val ageOfUser = editAge.text.toString()
            val classOfUser = editClass.text.toString()
            val majorOfUser = editMajor.text.toString()
            val bioOfUser = editBio.text.toString()
            val userFB = editFacebook.text.toString()
            val userIG = editInstagram.text.toString()
            val userTW = editTwitter.text.toString()
            val SM : ProfileResponse.SocialMedia = ProfileResponse.SocialMedia(userFB, userTW, userIG)
            val newUser : ProfileResponse.User = ProfileResponse.User(null, usernameOfUser, nameOfUser, ageOfUser, majorOfUser, classOfUser, bioOfUser, SM, photoURL)

            sendNetworkRequest(newUser);
        }

/*

*/
    }

    private fun sendNetworkRequest(userObject: ProfileResponse.User){
        val retrofit = Retrofit.Builder()
                .baseUrl("https://passionfruit-backend.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val apiClientService = retrofit.create(apiClient::class.java)
        apiClientService.saveUserData(userObject).enqueue(object: Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Report error here
                Log.d("MainActivity", "Could not save user data!")
            }
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if (!response.isSuccessful) {
                    // Report error here
                    Log.d("Status Code: ", response.code().toString())
                    Log.d("Status Code: ", response.message())
                    Log.d("MainActivity", "Response failed!")
                    return
                }
                val userResponse = response.body()
                        ?: // Report error here
                        return
                //Log.d("MainActivity", "userdata: " +(feedResponse?.users==null))
                if(userResponse != null) {
                    //Log.d("MainActivity", "feedresponse: " +feedResponse.toString())
                    uId = userResponse.userid!!
                }

                Toast.makeText(this@CreateProfileActivity, uId, Toast.LENGTH_SHORT).show()
                //sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
                PreferenceManager.getDefaultSharedPreferences(applicationContext).edit().putString("user_id", uId).apply();
                val intent : Intent = Intent(this@CreateProfileActivity, MainActivity::class.java)
                startActivity(intent)

            }


        });

    }

    /*

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*/
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    //val path = saveImage(bitmap)
                    Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()
                    imageview!!.setImageBitmap(bitmap)

                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }
        else if (requestCode == CAMERA)
        {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            imageview!!.setImageBitmap(thumbnail)
            //saveImage(thumbnail)
            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }

    */


}
