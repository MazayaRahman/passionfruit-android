package com.example.mazay.passionfruit.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.mazay.passionfruit.models.ProfileResponse

class FeedViewModel: ViewModel() {

    private val users: LiveData<List<ProfileResponse.User>>? = null


}