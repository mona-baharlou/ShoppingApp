package com.bahrlou.shoppingapp.model.repository.user

import android.content.SharedPreferences
import com.bahrlou.shoppingapp.model.net.ApiService
import com.bahrlou.shoppingapp.model.repository.TokenInMemory
import com.bahrlou.shoppingapp.util.SUCCESS
import com.google.gson.JsonObject

class UserRepositoryImpl(
    private val apiService: ApiService,
    private val sharedPref: SharedPreferences
) : UserRepository {
    override suspend fun signUp(name: String, username: String, password: String): String {

        val jsonObject = JsonObject().apply {
            addProperty("name", name)
            addProperty("email", username)
            addProperty("password", password)
        }
        val result = apiService.signUp(jsonObject)

        if (result.success) {

            //set Token in Memory
            TokenInMemory.refreshToken(username, result.token)

            //save in SharedPref
            saveToken(result.token)
            saveUsername(username)

            return SUCCESS
        } else {
            return result.message
        }

    }

    override suspend fun signIn(username: String, password: String): String {
        val jsonObject = JsonObject().apply {
            addProperty("email", username)
            addProperty("password", password)
        }

        val result = apiService.signIn(jsonObject)

        if (result.success) {

            //save token in sharedPref
            saveToken(result.token)
            saveUsername(username)

            return SUCCESS
        } else {
            return result.message
        }
    }


    //offline
    override fun signOut() {
        TokenInMemory.refreshToken(null, null)
        //remove from sharedPref
        sharedPref.edit().clear().apply()
    }

    override fun loadToken() {
        //user data exists in SharedPref but not exists in Memory cache
        //so loadToken saves data in Cache
        TokenInMemory.refreshToken(getUsername(), getToken())
    }

    override fun saveToken(newToken: String) {
        sharedPref.edit().putString("token", newToken).apply()
    }

    override fun getToken(): String {
        return sharedPref.getString("token", "")!!
    }

    override fun saveUsername(username: String) {
        sharedPref.edit().putString("username", username).apply()

    }

    override fun getUsername(): String {
        return sharedPref.getString("username", "")!!

    }
}