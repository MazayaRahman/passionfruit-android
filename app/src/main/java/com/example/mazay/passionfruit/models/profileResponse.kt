package com.example.mazay.passionfruit.models

import com.google.gson.annotations.SerializedName

class ProfileResponse {
    @SerializedName("user") var user: User?=null

    data class User(@SerializedName("user") val authToken: String,
                    val name: String,
                    val profileInfo: ProfileInfo,
                    val socialmedia: SocialMedia,
                    val userid: Int)

    data class SocialMedia(@SerializedName("social media") val facebook: String?)

    data class ProfileInfo(@SerializedName("profileInfo") val age: Int,
                           val gender: String)


}

