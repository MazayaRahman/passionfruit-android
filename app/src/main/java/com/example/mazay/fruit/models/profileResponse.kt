package com.example.mazay.fruit.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProfileResponse {

    @SerializedName("user") var user: User?=null

    data class User(@SerializedName("user") val _id: UserId?,
                    val username: String,
                    val name: String,
                    val age: String,
                    val major: String,
                    val year: String,
                    val bio: String,
                    val socials: SocialMedia,
                    val photo_url: String
                    //val profileInfo: ProfileInfo,
                    //val socialmedia: SocialMedia,
                    ) : Serializable

    data class UserId(val userid: String?) : Serializable

    data class SocialMedia( val facebook: String?,
                           val twitter: String?,
                           val instagram: String?) : Serializable




}

