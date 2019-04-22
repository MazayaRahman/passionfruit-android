package com.example.mazay.fruit.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProfileResponse {
    @SerializedName("user") var user: User?=null

    data class User(@SerializedName("user") val _id: UserId,
                    val username: String,
                    val name: String,
                    val age: String,
                    val major: String,
                    val year: String,
                    val socials: SocialMedia
                    //val profileInfo: ProfileInfo,
                    //val socialmedia: SocialMedia,
                    ) : Serializable

    data class UserId(@SerializedName("_id") val userid: String?) : Serializable

    data class SocialMedia(@SerializedName("socials") val facebook: String?,
                           val github: String?,
                           val instagram: String?) : Serializable

    data class ProfileInfo(@SerializedName("profileInfo") val age: Int,
                           val gender: String) : Serializable


}

