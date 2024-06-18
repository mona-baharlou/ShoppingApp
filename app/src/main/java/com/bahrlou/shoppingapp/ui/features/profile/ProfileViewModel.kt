package com.bahrlou.shoppingapp.ui.features.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bahrlou.shoppingapp.model.repository.user.UserRepository

class ProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val email = mutableStateOf("")
    val address = mutableStateOf("")
    val postalCode = mutableStateOf("")
    val loginTime = mutableStateOf("")

    fun getUserInfo() {
        email.value = userRepository.getUsername()!!
        loginTime.value = userRepository.getUserLoginTime()

        val location = userRepository.getUserLocation()!!
        address.value = location.first
        postalCode.value = location.second
    }

}