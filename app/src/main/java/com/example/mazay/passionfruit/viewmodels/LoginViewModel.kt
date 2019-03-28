package com.example.mazay.passionfruit.viewmodels

import android.arch.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel: ViewModel() {

    lateinit var auth : FirebaseAuth
}