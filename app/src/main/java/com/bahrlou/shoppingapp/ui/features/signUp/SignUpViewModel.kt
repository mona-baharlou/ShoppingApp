package com.bahrlou.shoppingapp.ui.features.signUp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {

    val name = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val confirmPassword = MutableLiveData("")


    fun userSignUp() {
        Log.d("SignUPP", "userSignUp: ${name.value}")
    }
}