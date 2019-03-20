package com.example.mazay.passionfruit.models

data class LoginResponse(
    val authToken: String,
    val success: Boolean,
    val userid: Int
)