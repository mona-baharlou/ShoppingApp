package com.bahrlou.shoppingapp.model.net

import com.bahrlou.shoppingapp.model.data.LoginResponse
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("signup")
    suspend fun signUp(@Body jsonObject: JsonObject): LoginResponse

    @POST("signup")
    suspend fun signIn(@Body jsonObject: JsonObject): LoginResponse
}