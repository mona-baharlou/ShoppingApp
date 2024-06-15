package com.bahrlou.shoppingapp.ui.features.signIn

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bahrlou.shoppingapp.model.repository.user.UserRepository

class SignInViewModel(private val userRepository: UserRepository) : ViewModel() {

    val email = MutableLiveData("")
    val password = MutableLiveData("")


    fun userSignIn() {
        Log.d("SignINN", "userSignUp: ${email.value}")
    }
}