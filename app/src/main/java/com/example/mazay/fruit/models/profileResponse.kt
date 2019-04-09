package com.example.mazay.fruit.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProfileResponse {
    @SerializedName("user") var user: User?=null

    data class User(@SerializedName("user") val authToken: String,
                    val name: String,
                    val profileInfo: ProfileInfo,
                    val socialmedia: SocialMedia,
                    val userid: Int) : Serializable

    data class SocialMedia(@SerializedName("social media") val facebook: String?) : Serializable

    data class ProfileInfo(@SerializedName("profileInfo") val age: Int,
                           val gender: String) : Serializable


}
