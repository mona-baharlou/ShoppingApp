package com.bahrlou.shoppingapp.ui.signIn

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {

    val email = MutableLiveData("")
    val password = MutableLiveData("")


    fun userSignIn() {
        Log.d("SignINN", "userSignUp: ${email.value}")
    }
}