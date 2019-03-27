package com.example.mazay.passionfruit.models

import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("userid") var userid: Int? = null
    @SerializedName("authToken") var authToken: String? = null
    @SerializedName("success") var success: Boolean? = null

}