package com.example.mazay.passionfruit.models

import com.google.gson.annotations.SerializedName

class FeedResponse {

    @SerializedName("count") var count: Int? = null
    @SerializedName("users") var users: List<ProfileResponse.User>? = null
}

