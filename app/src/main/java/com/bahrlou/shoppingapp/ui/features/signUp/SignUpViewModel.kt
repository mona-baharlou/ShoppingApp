package com.bahrlou.shoppingapp.ui.features.signUp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bahrlou.shoppingapp.model.repository.user.UserRepository

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {

    val name = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val confirmPassword = MutableLiveData("")


    fun userSignUp() {
        Log.d("SignUPP", "userSignUp: ${name.value}")
    }
}