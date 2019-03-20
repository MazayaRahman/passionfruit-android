package com.example.mazay.passionfruit.viewmodels


import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData
import com.example.mazay.passionfruit.models.ProfileResponse


class ProfileViewModel : ViewModel() {
    private var userId: String? = null
    private val user: LiveData<ProfileResponse.User>? = null

    fun getUser(): LiveData<ProfileResponse.User>? {
        return user
    }
}