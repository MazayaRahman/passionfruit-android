package com.example.mazay.fruit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import de.hdodenhof.circleimageview.CircleImageView
import android.app.AlertDialog
import android.content.Intent
import android.provider.MediaStore
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.mazay.fruit.models.FeedResponse
import com.example.mazay.fruit.service.apiClient
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

class CreateProfileActivity : AppCompatActivity() {
    private var imageview: CircleImageView? = null
    private val GALLERY = 1
    private val CAMERA = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)

        imageview = findViewById(R.id.profile_image) as CircleImageView

        imageview!!.setOnClickListener { showPictureDialog() }

        save_btn.setOnClickListener {
            val jsonObject = JsonObject()
            val nameOfUser = editName.text
            val ageOfUser = editAge.text
            val classOfUser = editClass.text
            val majorOfUser = editMajor.text
            val bioOfUser = editBio.text
            jsonObject.addProperty("name",nameOfUser.toString());
            jsonObject.addProperty("age",ageOfUser.toString());
            jsonObject.addProperty("year",classOfUser.toString());
            jsonObject.addProperty("major",majorOfUser.toString());
            jsonObject.addProperty("bio",bioOfUser.toString());

            sendNetworkRequest(jsonObject);
        }


    }

    private fun sendNetworkRequest(jsonObject: JsonObject){
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.myjson.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val apiClientService = retrofit.create(apiClient::class.java)
        apiClientService.saveUserData(jsonObject).enqueue(object: Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                // Report error here
                Log.d("MainActivity", t.toString())
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (!response.isSuccessful) {
                    // Report error here
                    Log.d("MainActivity", "Could not save user data!")
                    return
                }

                Toast.makeText(this@CreateProfileActivity, "User Successfully Create!", Toast.LENGTH_SHORT)

            }


        });
    }

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


}
