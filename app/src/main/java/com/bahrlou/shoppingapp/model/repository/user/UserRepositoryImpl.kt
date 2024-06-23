package com.bahrlou.shoppingapp.model.repository.user

import android.content.SharedPreferences
import com.bahrlou.shoppingapp.model.net.ApiService
import com.bahrlou.shoppingapp.model.repository.TokenInMemory
import com.bahrlou.shoppingapp.util.CLICK_TO_ADD
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
            saveUserLoginTime()

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

            TokenInMemory.refreshToken(username, result.token)


            //save token in sharedPref
            saveToken(result.token)
            saveUsername(username)
            saveUserLoginTime()

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

    //

    override fun saveToken(newToken: String) {
        sharedPref.edit().putString("token", newToken).apply()
    }

    override fun getToken(): String? {
        return sharedPref.getString("token", null)
    }

    override fun saveUsername(username: String) {
        sharedPref.edit().putString("username", username).apply()

    }

    override fun getUsername(): String? {
        return sharedPref.getString("username", null)

    }

    override fun saveUserLocation(address: String, postalCode: String) {
        sharedPref.edit().putString("address", address).apply()
        sharedPref.edit().putString("postalCode", postalCode).apply()
    }

    override fun getUserLocation(): Pair<String, String> {
        val address = sharedPref.getString("address", CLICK_TO_ADD)!!
        val postalCode = sharedPref.getString("postalCode", CLICK_TO_ADD)!!
        return Pair(address, postalCode)

    }

    override fun saveUserLoginTime() {
        val now = System.currentTimeMillis()
        sharedPref.edit().putString("login_time", now.toString()).apply()

    }

    override fun getUserLoginTime(): String {
        return sharedPref.getString("login_time", "0")!!
    }
}